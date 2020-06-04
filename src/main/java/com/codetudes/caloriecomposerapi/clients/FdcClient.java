package com.codetudes.caloriecomposerapi.clients;

import com.codetudes.caloriecomposerapi.clients.config.FdcClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;

@FeignClient(value = "foodDataCentralClient", url = "${foodDataCentral.apiEndpoint}", configuration = FdcClientConfig.class)
public interface FdcClient {

    @GetMapping("/food/{fdcId}")
    LinkedHashMap<String, Object> getFoodByFdcId(@PathVariable Long fdcId);
}