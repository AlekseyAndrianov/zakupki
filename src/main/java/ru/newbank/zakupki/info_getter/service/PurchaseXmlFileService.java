package ru.newbank.zakupki.info_getter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.newbank.zakupki.info_getter.domain.PurchaseXmlFile;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

import java.util.List;

@Service
@Transactional
public class PurchaseXmlFileService {

    @Autowired
    XmlFileRepository xmlFileRepository;
//
//    public List<PurchaseXmlFile> getXmlByPurchaseNumber(long purchase_number) {
//        return xmlFileRepository.findAllXmlById(purchase_number);
//    }

}
