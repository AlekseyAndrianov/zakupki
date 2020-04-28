package ru.newbank.zakupki.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;
import ru.newbank.zakupki.indexer.repos.ArchivesRepository;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;
import ru.newbank.zakupki.info_getter.repos.InfoRepository;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

import java.io.*;

@Service
public class IndexService {

    @Autowired
    private ArchivesRepository archivesRepository;
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private XmlFileRepository xmlFileRepository;

    public IndexService() {
        this.archivesRepository = archivesRepository;
        this.infoRepository = infoRepository;
        this.xmlFileRepository = xmlFileRepository;
    }

    public ArchivesForRegion getFirstByArchive_name(String name) {
        System.out.println("Name: " + name);
        return archivesRepository.findByArchiveName(name);
    }
//    ArchivesForRegion archivesForRegion = new ArchivesForRegion(fileToDB.getName(), );
//        archivesRepository.save(archivesForRegion);

    public void addFileToDB(File fileToDB) {
//        "fcsNotificationEP44_{номер извещения}_{номер закупки}.xml";
        String[] fileNamePieces = fileToDB.getName().split("_");
        Long purchaseNumber = Long.parseLong(fileNamePieces[1]);
        int noticeId = Integer.parseInt(fileNamePieces[2].replaceAll(".xml", ""));
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToDB));
            content = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PurchaseInfo purchaseInfo = new PurchaseInfo(purchaseNumber, noticeId);
        infoRepository.save(purchaseInfo);

        PurchaseXmlFile purchaseXmlFile = new PurchaseXmlFile(purchaseNumber, fileToDB.getName(), content);
        xmlFileRepository.save(purchaseXmlFile);
    }

    public PurchaseXmlFile findFirstByFileName(String fileName) {
        return xmlFileRepository.findFirstByFileName(fileName);
    }
}
