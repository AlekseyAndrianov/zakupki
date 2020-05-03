package ru.newbank.zakupki.indexer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.newbank.zakupki.indexer.service.IndexService;
import ru.newbank.zakupki.indexer.service.NotificationManager;
import ru.newbank.zakupki.indexer.service.Region;

import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("indexing")
@Log4j2
public class IndexController {

    private final IndexService indexService;

    private final NotificationManager notificationManager;

    @Autowired
    public IndexController(IndexService indexService, NotificationManager notificationManager) {
        this.indexService = indexService;
        this.notificationManager = notificationManager;
    }

    @GetMapping("accept-changes")
    public String putNewPurchases(@RequestParam String prefixKey_ns4) { //prefixKey_ns4=fcsNotificationEP
        log.info(String.format("Start indexing by file prefix key '%s'", prefixKey_ns4));
        long start = new Date().getTime();
        for (Region region : Region.values()) {
            Path folder = indexService.getFolderByRegion(region);
            notificationManager.manageChangesForRegion(folder, prefixKey_ns4);
        }
        long stop = new Date().getTime();
        return "DONE! \nProcess time = " + ((stop - start) / 1000) + " sec.";
    }

}
