package ru.newbank.zakupki.info_getter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

@Service
@Transactional
public class PurchaseXmlFileService {

    private final XmlFileRepository xmlFileRepository;

    @Autowired
    public PurchaseXmlFileService(XmlFileRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }

    public String getXmlByPurchaseNumber(Long purchaseNumber) {
        return xmlFileRepository.findByPurchaseNumber(purchaseNumber).getXmlFile();
    }


}
