package ru.newbank.zakupki.info_getter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.info_getter.repos.InfoRepository;

@Service
public class PurchaseInfoService {

    private final InfoRepository infoRepository;

    @Autowired
    public PurchaseInfoService(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public int getNotice_idByPurchase_number(Long purchaseNumber) {
        return infoRepository.findById(purchaseNumber).get().getNoticeId();
    }

}
