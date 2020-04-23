package ru.newbank.zakupki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
public class ZakupkiApplication {

	public static void main(String[] args) {
		System.setProperty("db.driver", "org.postgresql.Driver");
		System.setProperty("db.url", "jdbc:postgresql://127.0.0.1:60467/zakupki");
		System.setProperty("db.username", "postgres");
		System.setProperty("db.password", "1234");




		SpringApplication.run(ZakupkiApplication.class, args);
	}

}
