package ru.newbank.zakupki.info_getter.service;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.info_getter.domain.PurchaseInfo;
import ru.newbank.zakupki.info_getter.repos.InfoRepository;

@Service
public class PurchaseInfoService {

    @Autowired
    InfoRepository infoRepository;

//    public PurchaseInfo getInfoByPurchaseNumber(long purchase_number) {
//        return infoRepository.findPurchaseInfoByPurchase_number(purchase_number);
//    }

    public int getNotice_idByPurchase_number(Long purchaseNumber){
        return infoRepository.findById(purchaseNumber).get().getNoticeId();
    }

}
