package com.codetudes.caloriecomposerapi.clients;

import com.codetudes.caloriecomposerapi.clients.config.FdcClientConfig;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcSearchResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@FeignClient(value = "foodDataCentralClient", url = "${foodDataCentral.apiEndpoint}", configuration = FdcClientConfig.class)
public interface FdcClient {

    @GetMapping("/food/{fdcId}")
    LinkedHashMap<String, Object> getFoodByFdcId(@PathVariable String fdcId);

    @GetMapping("/foods/search")
    FdcSearchResultDTO search(
            @RequestParam(value="query") String query,
            @RequestParam(value="dataType") String dataType,
            @RequestParam(value="pageSize") Long pageSize,
            @RequestParam(value="pageNumber") Long pageNumber);

    @GetMapping("/foods")
    List<LinkedHashMap<String, Object>> getFoodsByFdcIds(@RequestParam(value="fdcIds") String fdcIds);

}