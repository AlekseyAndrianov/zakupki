package ru.newbank.zakupki;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.newbank.zakupki.service.ServiceApplication;

import static org.junit.Assert.assertNotNull;


@SpringBootTest(classes = ServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application.properties")
class ServiceApplicationTests {

    @Test
    public void environmentContainsProperties() {

        assertNotNull(System.getProperty("ZAKUPKI_DB_URL"));
        assertNotNull(System.getProperty("ZAKUPKI_DB_USERNAME"));
        assertNotNull(System.getProperty("ZAKUPKI_DB_PASSWORD"));

    }
}
