package ru.newbank.zakupki.indexer;

import ru.newbank.zakupki.indexer.manager.NotificationManager;
import ru.newbank.zakupki.indexer.manager.Region;

import java.nio.file.Path;

public class NotificationExecutor {
    public static void main(String[] args) {

        NotificationManager manager = new NotificationManager();
        for (Region region : Region.values()) {
            Path folder = manager.getFolderByRegion(region);
            manager.manageChangesForRegion(folder);
        }
    }
}
