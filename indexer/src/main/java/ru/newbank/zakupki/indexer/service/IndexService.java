package ru.newbank.zakupki.indexer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;
import ru.newbank.zakupki.indexer.domain.PurchaseInfo;
import ru.newbank.zakupki.indexer.domain.PurchaseXmlFile;
import ru.newbank.zakupki.indexer.repos.ArchivesRepository;
import ru.newbank.zakupki.indexer.repos.InfoRepository;
import ru.newbank.zakupki.indexer.repos.XmlFileRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@PropertySource("classpath:application.properties")
@Log4j2
public class IndexService {

    private final ArchivesRepository archivesRepository;
    private final InfoRepository infoRepository;
    private final XmlFileRepository xmlFileRepository;
    private AtomicBoolean isIndexingNow = new AtomicBoolean(false);

    @Value("${file.manager.root.url}")
    private String rootUrl;

    @Autowired
    public IndexService(ArchivesRepository archivesRepository,
                        InfoRepository infoRepository,
                        XmlFileRepository xmlFileRepository) {
        this.archivesRepository = archivesRepository;
        this.infoRepository = infoRepository;
        this.xmlFileRepository = xmlFileRepository;
    }

    public ArchivesForRegion getFirstByArchive_name(String name) {
        return archivesRepository.findByArchiveName(name);
    }

    public void addArchiveInfoToDB(String archiveName, String region) {
        ArchivesForRegion archivesForRegion = new ArchivesForRegion(archiveName, region, OffsetDateTime.now(ZoneId.systemDefault()));
        archivesRepository.save(archivesForRegion);
    }

    public void addAllFilesToDB(List<File> fileToDB, String prefixKey_ns4, String archiveName) {
        List<PurchaseInfo> purchaseInfoList = new ArrayList<>();
        List<PurchaseXmlFile> purchaseXmlFileList = new ArrayList<>();

        fileToDB.stream().forEach(file -> {
            String fileName = file.getName();
            fileName = fileName.substring(fileName.indexOf(prefixKey_ns4));
            String[] fileNamePieces = fileName.split("_");
            Long purchaseNumber = Long.parseLong(fileNamePieces[1]);
            int noticeId = Integer.parseInt(fileNamePieces[2].replaceAll(".xml", ""));
            String xmlContentAfterRemoveSignature = reduceFileContent(file);

            PurchaseInfo purchaseInfo = new PurchaseInfo(purchaseNumber, noticeId, OffsetDateTime.now(ZoneId.systemDefault()));
            purchaseInfoList.add(purchaseInfo);

            PurchaseXmlFile purchaseXmlFile = new PurchaseXmlFile(purchaseNumber, fileName, xmlContentAfterRemoveSignature);
            purchaseXmlFileList.add(purchaseXmlFile);
        });

        infoRepository.saveAll(purchaseInfoList);
        xmlFileRepository.saveAll(purchaseXmlFileList);

        log.info(String.format("Add %d xml-files to database from archive '%s'", fileToDB.size(), archiveName));

    }

    private String reduceFileContent(File xmlWithSignature) {
        StringWriter stringWriter = null;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlWithSignature);

            removeElement(document, "signature");
            removeElement(document, "ns3:signature");

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringWriter == null ? "" : stringWriter.toString();
    }

    private void removeElement(Document document, String signature) {
        NodeList nodes = document.getElementsByTagName(signature);
        int nodesLength = nodes.getLength();
        if (nodesLength > 0) {
            for (int i = nodes.getLength() - 1; i >= 0; i--) {
                nodes.item(i).setTextContent("Content was deleted");
            }
        }
    }

    public String index(String prefixKey_ns4) {
        if (isIndexingNow.get()) {
            String message = "Can't execute one more indexing process, please try again later!";
            log.warn(message);
            return message;
        }
        isIndexingNow.set(true);
        log.info(String.format("Start indexing by file prefix key '%s'", prefixKey_ns4));
        long start = System.currentTimeMillis();

        List<File> regions = Arrays.asList(Paths.get(rootUrl).toFile().listFiles());
        for (File region : regions) {
            if (region.isFile())
                continue;
            manageChangesForRegion(region, prefixKey_ns4);
        }
        long stop = System.currentTimeMillis();
        String message = "Finish indexing after " + ((stop - start) / 1000) + " sec.";
        log.info(message);
        isIndexingNow.set(false);
        return message;
    }

    public void manageChangesForRegion(File regionFolder, String prefixKey_ns4) {
        indexZip("currMonth", regionFolder, prefixKey_ns4);
        indexZip("prevMonth", regionFolder, prefixKey_ns4);

    }

    private void indexZip(String monthFolder, File regionFolder, String prefixKey_ns4) {

        File[] filesMonthFolder = Paths.get(regionFolder.getPath(), "notifications", monthFolder).toFile().listFiles();
        if (filesMonthFolder == null)
            return;
        List<File> files = Arrays.asList(filesMonthFolder);

        files.stream().filter(file -> { // проверяем прошел ли файл процедуру
            ArchivesForRegion archivesForRegion = getFirstByArchive_name(file.getName());
            return (archivesForRegion == null); // Проверка по названию архива
        }).forEach(file -> {
            List<File> filesFromZip = ZipManager.getFilesFromZip(file, prefixKey_ns4);

            addAllFilesToDB(filesFromZip, prefixKey_ns4, file.getName());
            addArchiveInfoToDB(file.getName(), regionFolder.getName());

            filesFromZip.stream().forEach(File::delete);
        });
    }
}
