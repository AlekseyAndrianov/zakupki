package ru.newbank.zakupki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.newbank.zakupki.service.domain.PurchaseCodeMapping;
import ru.newbank.zakupki.service.repos.PurchaseCodeMappingRepository;

@Service
public class PurchaseCodeMappingService {

    private final PurchaseCodeMappingRepository purchaseCodeMappingRepository;

    @Autowired
    public PurchaseCodeMappingService(PurchaseCodeMappingRepository purchaseCodeMappingRepository) {
        this.purchaseCodeMappingRepository = purchaseCodeMappingRepository;
    }

    public Long getPurchaseNumberByCode(String purchaseCode){
        PurchaseCodeMapping purchaseCodeMapping
                = purchaseCodeMappingRepository.findPurchaseCodeMappingByPurchaseCode(purchaseCode);

        return purchaseCodeMapping == null ? null : purchaseCodeMapping.getPurchaseNumber();
    }
}
