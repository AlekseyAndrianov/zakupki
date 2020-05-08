package ru.newbank.zakupki.indexer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndexerApplication {

    public static void main(String[] args) {
//		System.setProperty("ZAKUPKI_DB_URL", "jdbc:postgresql://localhost:5432/zakupki");
//		System.setProperty("ZAKUPKI_DB_USERNAME", "postgres");
//		System.setProperty("ZAKUPKI_DB_PASSWORD", "1234");
//		System.setProperty("ROOT_URL", "D:/Projects/Banking/");
        SpringApplication.run(IndexerApplication.class, args);
    }
}
