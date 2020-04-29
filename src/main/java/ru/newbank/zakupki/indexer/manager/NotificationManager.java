package ru.newbank.zakupki.indexer.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import ru.newbank.zakupki.indexer.domain.ArchivesForRegion;
import ru.newbank.zakupki.indexer.service.IndexService;
import ru.newbank.zakupki.info_getter.repos.XmlFileRepository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@PropertySource("classpath:application.properties")
public class NotificationManager {

    @Autowired
    IndexService indexService;

    @Value("${file.manager.root.url}")
    private String rootUrl;

    public Path getFolderByRegion(Region region) {
        System.out.println(rootUrl);
        return Paths.get(rootUrl, region.getName());
    }

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
                        System.out.println("File from archive: " + fileToDB.getName());
                        indexService.addFileToDB(fileToDB);
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
