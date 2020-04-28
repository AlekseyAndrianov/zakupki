package ru.newbank.zakupki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.newbank.zakupki.indexer.manager.NotificationManager;
import ru.newbank.zakupki.indexer.manager.Region;

import java.nio.file.Path;

@SpringBootApplication
public class ZakupkiApplication {

	static NotificationManager notificationManager;

	public ZakupkiApplication(@Autowired NotificationManager notificationManager) {
		ZakupkiApplication.notificationManager = notificationManager;
	}

	public static void main(String[] args) {
//		System.setProperty("db.driver", "org.postgresql.Driver");
//		System.setProperty("db.url", "jdbc:postgresql://localhost:5432/zakupki");
//		System.setProperty("db.username", "postgres");
//		System.setProperty("db.password", "1234");
		SpringApplication.run(ZakupkiApplication.class, args);

		for (Region region : Region.values()) {
			Path folder = notificationManager.getFolderByRegion(region);
			System.out.println(folder.getFileName());
			notificationManager.manageChangesForRegion(folder);
		}
	}

}
