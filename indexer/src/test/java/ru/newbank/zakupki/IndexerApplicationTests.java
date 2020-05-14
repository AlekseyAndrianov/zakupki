package ru.newbank.zakupki;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.newbank.zakupki.indexer.IndexerApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IndexerApplication.class)
@PropertySource("classpath:application.properties")
class IndexerApplicationTests {

//    @Value("${file.manager.root.url}")
//    private String rootUrl;

//    @Test
//    public void environmentContainsProperties() {
//
//        assertNotNull(System.getProperty("ZAKUPKI_DB_URL"));
//        assertNotNull(System.getProperty("ZAKUPKI_DB_USERNAME"));
//        assertNotNull(System.getProperty("ZAKUPKI_DB_PASSWORD"));
//        assertNotNull(System.getProperty("ROOT_URL"));
//
//    }
//
//    @Test
//    public void isRootFolderExists() {
//        assertTrue(Files.isDirectory(Paths.get(rootUrl)));
//    }
}
