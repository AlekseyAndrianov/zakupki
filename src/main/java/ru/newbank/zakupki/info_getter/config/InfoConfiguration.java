package ru.newbank.zakupki.info_getter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.newbank.zakupki")
public class InfoConfiguration implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(@Autowired ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(true).
//                favorParameter(true).
//                parameterName("mediaType").
//                ignoreAcceptHeader(false).
//                useJaf(false).
//                defaultContentType(MediaType.APPLICATION_XML).
//                mediaType("xml", MediaType.APPLICATION_XML).
//                mediaType("json", MediaType.APPLICATION_JSON);
    }

}
