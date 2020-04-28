package ru.newbank.zakupki.info_getter.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.newbank.zakupki.info_getter.service.PurchaseInfoService;
import ru.newbank.zakupki.info_getter.service.PurchaseXmlFileService;

@RestController
@RequestMapping("info/")
public class InfoController {

    /*
     По номеру извещения `purchaseNumber` из БД находит соответствующий `noticeId`;
- получает xml-документ со сведениями о закупке с адреса https://zakupki.gov.ru/epz/order/notice/printForm/viewXml.html?noticeId=_{noticeId}_,
если эти сведения не хранятся в БД;
- перекодирует полученный xml-документ в json и возвращает его, или непосредственно xml-документ, если это было определено при Content negotiation
     */

    @Autowired
    PurchaseInfoService purchaseInfoService;

    @Autowired
    PurchaseXmlFileService purchaseXmlFileService;

    @Value("${zakupki.url}")
    private String baseUrl;

    @GetMapping(
            value = "notice/xml/{purchaseNumber}",
            headers = "Accept=application/json",
            produces = {"application/json; application/xml; charset=UTF-8"}
    )
    public String getNoticeByPurchaseNumber(@PathVariable Long purchaseNumber, @Nullable @RequestParam("mediaType") String mediaType) {
        String xmlFromTable = purchaseXmlFileService.getXmlByPurchaseNumber(purchaseNumber);
        String result = null;
        if (xmlFromTable != null)
            result = xmlFromTable;
        else {
            int noticeId = purchaseInfoService.getNotice_idByPurchase_number(purchaseNumber);
            String url = baseUrl + "?noticeId=" + noticeId;
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            result = responseEntity.getBody();

        }
        if (mediaType != null && mediaType.equals("json")) {
            JSONObject xmlJSONObj = XML.toJSONObject(result);
            result = xmlJSONObj.toString();
        }
        return result;
    }

}
