package com.codetudes.caloriecomposerapi.clients.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FdcClientConfig {

    @Value("${foodDataCentral.apiKey}")
    private String apiKey;

//    @Bean
//    private Params fcdQueryParams() {
//        Params params = new Params();
//        params.setApiKey(apiKey);
//        return params;
//    }

    @Bean
    RequestInterceptor fdcClientRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("apiKey", apiKey);
        };
    }

}
