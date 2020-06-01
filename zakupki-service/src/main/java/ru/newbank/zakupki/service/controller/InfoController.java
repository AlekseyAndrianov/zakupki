package ru.newbank.zakupki.service.controller;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.newbank.zakupki.service.domain.PurchaseInfo;
import ru.newbank.zakupki.service.service.InfoService;
import ru.newbank.zakupki.service.service.PurchaseCodeMappingService;
import ru.newbank.zakupki.service.service.PurchaseInfoService;
import ru.newbank.zakupki.service.service.PurchaseXmlFileService;

@RestController
@RequestMapping("info/")
@Log4j2
public class InfoController {

    private final PurchaseXmlFileService purchaseXmlFileService;
    private final PurchaseCodeMappingService purchaseCodeMappingService;
    private final InfoService infoService;

    @Autowired
    public InfoController(
            PurchaseXmlFileService purchaseXmlFileService,
            InfoService infoService,
            PurchaseCodeMappingService purchaseCodeMappingService) {
        this.purchaseXmlFileService = purchaseXmlFileService;
        this.infoService = infoService;
        this.purchaseCodeMappingService = purchaseCodeMappingService;
    }

    @GetMapping(
            value = "purchase_number/{purchaseNumber}",
            headers = "Accept=application/json",
            produces = {"application/json; application/xml; charset=UTF-8"}
    )
    public String getNoticeByPurchaseNumber(
            @PathVariable Long purchaseNumber,
            @RequestParam(value = "mediaType", required = false) String mediaType) {

        String xmlFromTable = purchaseXmlFileService.getXmlByPurchaseNumber(purchaseNumber);
        return infoService.findResult(purchaseNumber, mediaType, xmlFromTable);
    }


    @GetMapping(
            value = "purchase_code/{purchaseCode}",
            headers = "Accept=application/json",
            produces = {"application/json; application/xml; charset=UTF-8"}
    )
    public String getNoticeByPurchaseCode(
            @PathVariable String purchaseCode,
            @RequestParam(value = "mediaType", required = false) String mediaType) {

        Long purchaseNumber = purchaseCodeMappingService.getPurchaseNumberByCode(purchaseCode);
        if (purchaseNumber == null)
            return "No such purchase code in the database";
        String xmlFromTable = purchaseXmlFileService.getXmlByPurchaseNumber(purchaseNumber);
        return infoService.findResult(purchaseNumber, mediaType, xmlFromTable);
    }

}
