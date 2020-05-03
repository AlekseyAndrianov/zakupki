package ru.newbank.zakupki.indexer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
public class NotificationManager {

    private final IndexService indexService;

    @Autowired
    public NotificationManager(IndexService indexService) {
        this.indexService = indexService;
    }

    public void manageChangesForRegion(Path regionFolder, String prefixKey_ns4) {
        List<File> files = Arrays.asList(regionFolder.toFile().listFiles());
        log.info(String.format("Start indexing %d files for '%s' folder", files.size(), regionFolder.getFileName()));
        files.stream().filter(file -> { // проверяем прошел ли файл процедуру
            ArchivesForRegion archivesForRegion = indexService.getFirstByArchive_name(file.getName());
            return (archivesForRegion == null); // Проверка по названию архива
        }).forEach(file -> {
            indexService.addArchiveInfoToDB(file.getName(), regionFolder.getFileName().toString()); // Добавляем информацию об архиве
            log.info(String.format("File '%s' add to database and start unzipping", file.getName()));
            List<File> filesFromZip = ZipManager.getFilesFromZip(file);
            filesFromZip.stream()
                    .filter(
                            fileToFiler -> isTargetFile(fileToFiler.getName(), prefixKey_ns4)
                    ).forEach(
                    fileToDB -> {
                        String fileName = fileToDB.getName();
                        fileName = fileName.substring(fileName.indexOf(prefixKey_ns4));
                        indexService.addFileToDB(fileToDB, fileName);
                        log.info(String.format("XML-file '%s' add to database", fileName));
                    }
            );
        });
    }

    private boolean isTargetFile(String fileName, String prefixKey_ns4) {
        String postMask = ".xml";
        return fileName.contains(prefixKey_ns4) && fileName.contains(postMask);
    }
}
