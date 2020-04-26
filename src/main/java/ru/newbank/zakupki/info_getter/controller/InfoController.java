package ru.newbank.zakupki.info_getter.controller;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.newbank.zakupki.info_getter.service.PurchaseInfoService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLXML;
import java.util.Arrays;

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

    @GetMapping(value = "notice/xml/{purchaseNumber}", produces = MediaType.APPLICATION_XML_VALUE
    )
    public String getNoticeByPurchaseNumber(@PathVariable Long purchaseNumber) {
        int noticeId = purchaseInfoService.getNotice_idByPurchase_number(purchaseNumber);
        String url = baseUrl + "?noticeId=" + noticeId;
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Content-Type", MediaType.APPLICATION_XML_VALUE);
//        httpHeaders.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return responseEntity.getBody();
    }

    @GetMapping("notice/0")
    public String getNoticeByPurchaseNumber2() {

        return "Hello!";
    }


}
