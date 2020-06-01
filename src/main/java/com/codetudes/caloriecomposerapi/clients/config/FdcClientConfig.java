package com.codetudes.caloriecomposerapi.clients.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FdcClientConfig {

    @Value("${foodDataCentral.apiKey}")
    private String apiKey;

    @Bean
    RequestInterceptor fdcClientRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("api_key", apiKey);
        };
    }

}
