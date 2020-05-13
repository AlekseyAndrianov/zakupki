package ru.newbank.zakupki.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

//    static {
//        System.setProperty("ZAKUPKI_DB_URL", "jdbc:postgresql://localhost:5432/zakupki");
//        System.setProperty("ZAKUPKI_DB_USERNAME", "postgres");
//        System.setProperty("ZAKUPKI_DB_PASSWORD", "1234");
//    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
