package ru.newbank.zakupki.indexer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            List<File> filesFromZip = ZipManager.getFilesFromZip(file);

            List<File> filesFilteredToDB =
                    filesFromZip.stream()
                    .filter(
                            fileToFiler -> isTargetFile(fileToFiler.getName(), prefixKey_ns4)
                    ).collect(Collectors.toList());

            indexService.addAllFilesToDB(filesFilteredToDB, prefixKey_ns4, file.getName());
            indexService.addArchiveInfoToDB(file.getName(), regionFolder.getFileName().toString());

        });
    }

    private boolean isTargetFile(String fileName, String prefixKey_ns4) {
        String postMask = ".xml";
        return fileName.contains(prefixKey_ns4) && fileName.contains(postMask);
    }
}
