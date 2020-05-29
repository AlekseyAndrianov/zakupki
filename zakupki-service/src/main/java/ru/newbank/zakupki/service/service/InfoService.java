package ru.newbank.zakupki.service.service;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class InfoService {

    private final PurchaseInfoService purchaseInfoService;

    @Value("${zakupki.url}")
    private String baseUrl;

    @Autowired
    public InfoService(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }

    public String findResult(Long purchaseNumber, String mediaType, String xmlFromTable) {
        String result;
        String xmlResource;
        if (xmlFromTable != null) {
            result = xmlFromTable;
            xmlResource = "database";
        } else {
            Integer noticeId = purchaseInfoService.getNoticeIdByPurchaseNumber(purchaseNumber);
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
