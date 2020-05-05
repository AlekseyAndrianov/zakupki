package ru.newbank.zakupki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZakupkiApplication {

    public static void main(String[] args) {
		System.setProperty("db.driver", "org.postgresql.Driver");
		System.setProperty("db.url", "jdbc:postgresql://localhost:5432/zakupki");
		System.setProperty("db.username", "postgres");
		System.setProperty("db.password", "1234");
        SpringApplication.run(ZakupkiApplication.class, args);
    }

}
