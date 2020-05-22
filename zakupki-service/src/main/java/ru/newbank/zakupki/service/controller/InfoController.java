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
import ru.newbank.zakupki.service.service.PurchaseInfoService;
import ru.newbank.zakupki.service.service.PurchaseXmlFileService;

@RestController
@RequestMapping("info/")
@Log4j2
public class InfoController {

    private final PurchaseInfoService purchaseInfoService;
    private final PurchaseXmlFileService purchaseXmlFileService;

    @Value("${zakupki.url}")
    private String baseUrl;

    @Autowired
    public InfoController(PurchaseInfoService purchaseInfoService, PurchaseXmlFileService purchaseXmlFileService) {
        this.purchaseInfoService = purchaseInfoService;
        this.purchaseXmlFileService = purchaseXmlFileService;
    }

    @GetMapping(
            value = "notice/xml/{purchaseNumber}",
            headers = "Accept=application/json",
            produces = {"application/json; application/xml; charset=UTF-8"}
    )
    public String getNoticeByPurchaseNumber(@PathVariable Long purchaseNumber, @RequestParam(value = "mediaType", required = false) String mediaType) {
        String xmlFromTable = purchaseXmlFileService.getXmlByPurchaseNumber(purchaseNumber);
        String result;
        String xmlResource;
        if (xmlFromTable != null) {
            result = xmlFromTable;
            xmlResource = "database";
        } else {
            Integer noticeId = purchaseInfoService.getNotice_idByPurchase_number(purchaseNumber);
            if (noticeId == null) {
                log.error(String.format("PurchaseNumber: '%d' was NOT found in the database.", purchaseNumber));
                return "No such PurchaseNumber in the DataBase";
            }
            String url = baseUrl + "?noticeId=" + noticeId;
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            result = responseEntity.getBody();
            xmlResource = "zakupki.gov.ru";

        }
        if (mediaType != null && mediaType.equals("json")) {
            JSONObject xmlJSONObj = XML.toJSONObject(result);
            result = xmlJSONObj.toString();
        }
        log.info(String.format("Get request for purchaseNumber: '%d' with mediaType: '%s' was successful processed. " +
                        "XML-document returned from %s",
                purchaseNumber,
                mediaType == null ? "xml" : mediaType,
                xmlResource));
        return result;
    }

}
