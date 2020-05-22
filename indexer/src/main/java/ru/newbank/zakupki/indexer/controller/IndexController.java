package ru.newbank.zakupki.indexer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.newbank.zakupki.indexer.service.IndexService;

@RestController
@RequestMapping("indexing")
@Log4j2
@PropertySource("classpath:application.properties")
public class IndexController {

    @Value("${prefixKey_ns4}")
    String appPrefixKey_ns4;

    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("accept-changes")
    public String makeIndexingByGetRequest(@RequestParam(required = false) String prefixKey_ns4) { //prefixKey_ns4=fcsNotificationEP
        return indexService.index(prefixKey_ns4 == null ? appPrefixKey_ns4 : prefixKey_ns4);
    }

    @Scheduled(initialDelayString = "${indexer.schedule.initial.delay}", fixedRateString = "${indexer.schedule.rate}")
    public void makeIndexingBySchedule() {
        indexService.index(appPrefixKey_ns4);
    }
}
