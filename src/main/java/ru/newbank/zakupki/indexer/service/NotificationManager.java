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

    @Autowired
    IndexService indexService;

    public void manageChangesForRegion(Path regionFolder, String prefixKey_ns4) {
        List<File> files = Arrays.asList(regionFolder.toFile().listFiles());
        files.stream().filter(file -> { // проверяем прошел ли файл процедуру
            System.out.println("File name: " + file.getName());
            ArchivesForRegion archivesForRegion = indexService.getFirstByArchive_name(file.getName());
            System.out.println("ArchivesForRegion: " + archivesForRegion);
            return (archivesForRegion == null); // Проверка по названию архива
        }).forEach(file -> {
            indexService.addArchiveInfoToDB(file.getName(), regionFolder.getFileName().toString()); // Добавляем информацию об архиве
            List<File> filesFromZip = ZipManager.getFilesFromZip(file);
            filesFromZip.stream()
                    .filter(
                            fileToFiler -> isTargetFile(fileToFiler.getName(), prefixKey_ns4)
                    ).forEach(
                    fileToDB -> {
                        String fileName = fileToDB.getName();
                        fileName = fileName.substring(fileName.indexOf(prefixKey_ns4));
                        System.out.println("File from archive: " + fileName);
                        indexService.addFileToDB(fileToDB, fileName);
                    }
            );
        });
    }

    private boolean isTargetFile(String fileName, String prefixKey_ns4) {
        String postMask = ".xml";
        boolean relativeName = fileName.contains(prefixKey_ns4) && fileName.contains(postMask);
        System.out.println("relativeName: " + relativeName + "; " + fileName);
        return relativeName;
    }


    /*
    1. Получает из параметров системы путь и доступ к папке с архивами
    2. Начинает обход файлов с последней даты до конца списка, помечая проработанные файлы
    3. Заходит в файл, фильтрует по маске через стрим (м.б.)
    4. Залетает в БД, находит запись по номеру извещения, проверяет ее корректность, если записи нет, создает в 2х таблицах
    5. Создать еще одну таблицу (с названием файлов - архивов, месяцем, регионом) если архива ренее не было, то фиксируем в таблице, если был, то не заходим в него
     */


}
