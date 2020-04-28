package ru.newbank.zakupki.indexer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.newbank.zakupki.indexer.repos.ArchivesRepository;
import ru.newbank.zakupki.indexer.service.IndexService;

@Configuration
@EnableWebMvc
public class IndexerConfiguration {

    @Bean
    IndexService getIndexService(){
        return new IndexService();
    }
}
