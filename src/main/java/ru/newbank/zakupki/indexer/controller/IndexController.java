package ru.newbank.zakupki.indexer.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.newbank.zakupki.indexer.manager.NotificationManager;
import ru.newbank.zakupki.indexer.manager.Region;
import ru.newbank.zakupki.indexer.service.IndexService;
import ru.newbank.zakupki.indexer.service.XmlPurchaseSet;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void putNewPurchases(@RequestParam String prefixKey_ns4) { //fcsNotificationEP

        for (Region region : Region.values()) {
            Path folder = notificationManager.getFolderByRegion(region);
            System.out.println(folder.getFileName());
            notificationManager.manageChangesForRegion(folder, prefixKey_ns4);
        }
    }

}
