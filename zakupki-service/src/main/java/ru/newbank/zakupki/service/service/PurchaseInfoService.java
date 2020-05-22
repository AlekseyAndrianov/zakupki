package ru.newbank.zakupki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.service.domain.PurchaseInfo;
import ru.newbank.zakupki.service.repos.InfoRepository;

@Service
public class PurchaseInfoService {

    private final InfoRepository infoRepository;

    @Autowired
    public PurchaseInfoService(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public Integer getNotice_idByPurchase_number(Long purchaseNumber) {
        PurchaseInfo purchaseInfo = infoRepository.findByPurchaseNumber(purchaseNumber);
        return purchaseInfo == null ? null : purchaseInfo.getNoticeId();
    }

}
