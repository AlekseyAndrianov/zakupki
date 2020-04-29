package ru.newbank.zakupki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.newbank.zakupki.indexer.service.NotificationManager;

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
	}

}
