package ru.newbank.zakupki.indexer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.newbank.zakupki.indexer.service.NotificationManager;
import ru.newbank.zakupki.indexer.service.Region;
import ru.newbank.zakupki.indexer.service.IndexService;

import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("indexing")
public class IndexController {

    final IndexService indexService;

    final NotificationManager notificationManager;

    @Autowired
    public IndexController(IndexService indexService, NotificationManager notificationManager) {
        this.indexService = indexService;
        this.notificationManager = notificationManager;
    }

    @GetMapping("accept-changes")
    public String putNewPurchases(@RequestParam String prefixKey_ns4) { //prefixKey_ns4=fcsNotificationEP
        long start = new Date().getTime();
        for (Region region : Region.values()) {
            Path folder = indexService.getFolderByRegion(region);
            System.out.println(folder.getFileName());
            notificationManager.manageChangesForRegion(folder, prefixKey_ns4);
        }
        long stop = new Date().getTime();
        return "DONE! \nProcess time = " + ((stop - start) / 1000) + " sec.";
    }

}
