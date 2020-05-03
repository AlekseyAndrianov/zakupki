package ru.newbank.zakupki.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;
import ru.newbank.zakupki.indexer.repos.ArchivesRepository;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;
import ru.newbank.zakupki.info_getter.repos.InfoRepository;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@PropertySource("classpath:application.properties")
public class IndexService {

    private final ArchivesRepository archivesRepository;
    private final InfoRepository infoRepository;
    private final XmlFileRepository xmlFileRepository;

    @Value("${file.manager.root.url}")
    private String rootUrl;

    @Autowired
    public IndexService(ArchivesRepository archivesRepository, InfoRepository infoRepository, XmlFileRepository xmlFileRepository) {
        this.archivesRepository = archivesRepository;
        this.infoRepository = infoRepository;
        this.xmlFileRepository = xmlFileRepository;
    }

    public ArchivesForRegion getFirstByArchive_name(String name) {
        return archivesRepository.findByArchiveName(name);
    }

    public Path getFolderByRegion(Region region) {
        return Paths.get(rootUrl, region.getName());
    }

    public void addArchiveInfoToDB(String archiveName, String region) {
        ArchivesForRegion archivesForRegion = new ArchivesForRegion(archiveName, region, OffsetDateTime.now(ZoneId.systemDefault()));
        archivesRepository.save(archivesForRegion);
    }

    public void addFileToDB(File fileToDB, String fileName) {
        String[] fileNamePieces = fileName.split("_");
        Long purchaseNumber = Long.parseLong(fileNamePieces[1]);
        int noticeId = Integer.parseInt(fileNamePieces[2].replaceAll(".xml", ""));
        String xmlContentAfterRemoveSignature = reduceFileContent(fileToDB);

        PurchaseInfo purchaseInfo = new PurchaseInfo(purchaseNumber, noticeId, OffsetDateTime.now(ZoneId.systemDefault()));
        infoRepository.save(purchaseInfo);

        PurchaseXmlFile purchaseXmlFile = new PurchaseXmlFile(purchaseNumber, fileName, xmlContentAfterRemoveSignature);
        xmlFileRepository.save(purchaseXmlFile);
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
}
