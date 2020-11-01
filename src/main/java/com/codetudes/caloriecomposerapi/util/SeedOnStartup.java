package com.codetudes.caloriecomposerapi.util;

import com.codetudes.caloriecomposerapi.contracts.ComboFoodConstituentDTO;
import com.codetudes.caloriecomposerapi.contracts.ComboFoodConversionRatioDTO;
import com.codetudes.caloriecomposerapi.contracts.ComboFoodDTO;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.ComboFoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.ComboFoodService;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedOnStartup {

    static final Logger LOG = LoggerFactory.getLogger(SeedOnStartup.class);

    @Value("${db-seeding.seed-on-startup}")
    private Boolean seedOnStartup;

    @Value("${db-seeding.truncate-tables-first}")
    private Boolean truncateTablesFirst;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboFoodRepository comboFoodRepository;

    @Autowired
    private FdcService fdcService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private ComboFoodService comboFoodService;


    @EventListener
    public void onContextRefreshd(ContextRefreshedEvent event){
        LOG.info("db-seeding.seed-on-startup: {}", seedOnStartup);
        if (seedOnStartup){
            LOG.info("db-seeding.truncate-tables-first: {}", truncateTablesFirst);
            if (truncateTablesFirst){
                truncateTables();
            }
            seed();
        }
    }

    private void truncateTables() {
        userRepository.deleteAll();

        // food, nutrient, portion
        foodRepository.deleteAll();

        // combo_food, combo_food_conversion_ratio, combo_food_constituent
        comboFoodRepository.deleteAll();
    }

    private void seed() {
        // first create user
        User user = new User();
        user.setUsername("username");
        userRepository.save(user);

        // PBJ Food items
        FoodDTO peanutButterDTO = foodService.create(fdcService.getFoodByFdcId("358350"));
        FoodDTO jellyDTO = foodService.create(fdcService.getFoodByFdcId("386995"));
        FoodDTO breadDTO = foodService.create(fdcService.getFoodByFdcId("406269"));

        // PBJ Combo Food
        ComboFoodDTO pbjDTO = new ComboFoodDTO();
        pbjDTO.setIsDraft(false);
        pbjDTO.setDescription("Peanut Butter Jelly Sandwich");

        // PBJ Combo Food - conversion ratios
        List<ComboFoodConversionRatioDTO> conversionRatios = new ArrayList();
        pbjDTO.setConversionRatios(conversionRatios);

        ComboFoodConversionRatioDTO cfCvRat1 = new ComboFoodConversionRatioDTO();
        cfCvRat1.setAmountA(new BigDecimal("1"));
        cfCvRat1.setUnitA("Sandwich");
        cfCvRat1.setAmountB(new BigDecimal("1"));
        cfCvRat1.setUnitB("SERVING_SIZE_REF");
        conversionRatios.add(cfCvRat1);

        ComboFoodConversionRatioDTO cfCvRat2 = new ComboFoodConversionRatioDTO();
        cfCvRat2.setAmountA(new BigDecimal("1"));
        cfCvRat2.setUnitA("CONSTITUENTS_SIZE_REF");
        cfCvRat2.setAmountB(new BigDecimal("1"));
        cfCvRat2.setUnitB("SERVING_SIZE_REF");
        conversionRatios.add(cfCvRat2);

        // PBJ Combo Food - constituents
        List<ComboFoodConstituentDTO> constituents = new ArrayList();
        pbjDTO.setConstituents(constituents);

        ComboFoodConstituentDTO peanutButterCst = new ComboFoodConstituentDTO();
        peanutButterCst.setFood(peanutButterDTO);
        peanutButterCst.setAmount(new BigDecimal("3"));
        peanutButterCst.setUnit("Tbs");
        constituents.add(peanutButterCst);

        ComboFoodConstituentDTO jellyCst = new ComboFoodConstituentDTO();
        jellyCst.setFood(jellyDTO);
        jellyCst.setAmount(new BigDecimal("1"));
        jellyCst.setUnit("Tbs");
        constituents.add(jellyCst);

        ComboFoodConstituentDTO breadCst = new ComboFoodConstituentDTO();
        breadCst.setFood(breadDTO);
        breadCst.setAmount(new BigDecimal("98")); // 2 slices
        breadCst.setUnit("g");
        constituents.add(breadCst);

        comboFoodService.create(pbjDTO);
    }
}
