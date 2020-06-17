package com.codetudes.caloriecomposerapi.config;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: lock down before deploying publicly
        registry.addMapping("/**");
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean()
    FoodPatchMapper foodPatchMapper() {
        FoodPatchMapper patchMapper = new FoodPatchMapper();
        patchMapper.typeMap(FoodDTO.class, Food.class).addMappings(mapper -> mapper.skip(Food::setNutrients));
        return patchMapper;
    }

    public class FoodPatchMapper extends ModelMapper { }

}
