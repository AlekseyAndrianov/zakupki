package ru.newbank.zakupki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.service.domain.PurchaseXmlFile;
import ru.newbank.zakupki.service.repos.XmlFileRepository;

@Service
@Transactional
public class PurchaseXmlFileService {

    private final XmlFileRepository xmlFileRepository;

    @Autowired
    public PurchaseXmlFileService(XmlFileRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }

    public String getXmlByPurchaseNumber(Long purchaseNumber) {
        PurchaseXmlFile purchaseXmlFile = xmlFileRepository.findByPurchaseNumber(purchaseNumber);
        return purchaseXmlFile == null ? null : purchaseXmlFile.getXmlFile();
    }

}
