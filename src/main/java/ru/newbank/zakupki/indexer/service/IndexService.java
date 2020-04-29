package ru.newbank.zakupki.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;
import ru.newbank.zakupki.indexer.repos.ArchivesRepository;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;
import ru.newbank.zakupki.info_getter.repos.InfoRepository;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@PropertySource("classpath:application.properties")
public class IndexService {

    @Autowired
    private ArchivesRepository archivesRepository;
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private XmlFileRepository xmlFileRepository;
    @Value("${file.manager.root.url}")
    private String rootUrl;

    public ArchivesForRegion getFirstByArchive_name(String name) {
        System.out.println("Name: " + name);
        return archivesRepository.findByArchiveName(name);
    }

    public Path getFolderByRegion(Region region) {
        System.out.println(rootUrl);
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
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToDB));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PurchaseInfo purchaseInfo = new PurchaseInfo(purchaseNumber, noticeId, OffsetDateTime.now(ZoneId.systemDefault()));
        infoRepository.save(purchaseInfo);

        PurchaseXmlFile purchaseXmlFile = new PurchaseXmlFile(purchaseNumber, fileName, content.toString());
        xmlFileRepository.save(purchaseXmlFile);
    }

    public PurchaseXmlFile findFirstByFileName(String fileName) {
        return xmlFileRepository.findFirstByFileName(fileName);
    }
}
