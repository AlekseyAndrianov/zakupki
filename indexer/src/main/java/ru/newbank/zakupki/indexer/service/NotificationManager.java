package ru.newbank.zakupki.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Controller
public class NotificationManager {

    private final IndexService indexService;

    @Autowired
    public NotificationManager(IndexService indexService) {
        this.indexService = indexService;
    }

    public void manageChangesForRegion(Path regionFolder, String prefixKey_ns4) {
        List<File> files = Arrays.asList(regionFolder.toFile().listFiles());
        files.stream().filter(file -> { // проверяем прошел ли файл процедуру
            ArchivesForRegion archivesForRegion = indexService.getFirstByArchive_name(file.getName());
            return (archivesForRegion == null); // Проверка по названию архива
        }).forEach(file -> {
            List<File> filesFromZip = ZipManager.getFilesFromZip(file, prefixKey_ns4);

            indexService.addAllFilesToDB(filesFromZip, prefixKey_ns4, file.getName());
            indexService.addArchiveInfoToDB(file.getName(), regionFolder.getFileName().toString());

            filesFromZip.stream().forEach(File::delete);
        });
    }
}
