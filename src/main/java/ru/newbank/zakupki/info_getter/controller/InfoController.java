package ru.newbank.zakupki.info_getter.controller;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.newbank.zakupki.info_getter.service.PurchaseInfoService;

import java.sql.SQLXML;

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

    @Value("${zakupki.url}")
    private String baseUrl;

    @GetMapping("notice/xml/{purchaseNumber}")
    public String getNoticeByPurchaseNumber(@PathVariable Long purchaseNumber) {
        int noticeId = purchaseInfoService.getNotice_idByPurchase_number(purchaseNumber);
        String url = baseUrl + "?noticeId=" + noticeId;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, entity, String.class);
        return responseEntity.getBody();
    }

    @GetMapping("notice/0")
    public String getNoticeByPurchaseNumber2() {

        return "Hello!";
    }


}
