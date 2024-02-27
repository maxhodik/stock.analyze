package com.example.maxhodik.stock.analyze.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Value("${number.of.companies}")
    private Integer NUMBER_OF_COMPANIES;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Integer getNumberOfCompaniesConfig() {
        return NUMBER_OF_COMPANIES;
    }

}
