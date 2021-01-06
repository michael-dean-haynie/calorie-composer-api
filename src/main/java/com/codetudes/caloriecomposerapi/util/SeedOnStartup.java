package com.codetudes.caloriecomposerapi.util;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.ComboFoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.ComboFoodService;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedOnStartup {

    static final Logger LOG = LoggerFactory.getLogger(SeedOnStartup.class);

    @Value("${db-seeding.seed-on-startup:false}")
    private Boolean seedOnStartup;
    @Value("${db-seeding.truncate-tables-first:false}")
    private Boolean truncateTablesFirst;
    @Value("${db-seeding.seed-fdc-data:false}")
    private Boolean seedFdcData;
    @Value("${db-seeding.seed-json-data:false}")
    private Boolean seedJsonData;

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:seeding-data/peanut-butter.json")
    Resource peanutButterJson;
    @Value("classpath:seeding-data/jelly.json")
    Resource jellyJson;
    @Value("classpath:seeding-data/bread.json")
    Resource breadJson;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboFoodRepository comboFoodRepository;
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private FdcService fdcService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private ComboFoodService comboFoodService;

    private List<User> seededUsers = new ArrayList<>();


    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event){
        LOG.info("db-seeding.seed-on-startup: {}", seedOnStartup);
        if (seedOnStartup){
            LOG.info("db-seeding.truncate-tables-first: {}", truncateTablesFirst);
            if (truncateTablesFirst){
                truncateTables();
            }

            if (userRepository.count() == 0){
                seedUser();
            }

            if (unitRepository.count() == 0) {
                seedUnits();
            }

            LOG.info("db-seeding.seed-fdc-data: {}", seedFdcData);
            if (seedFdcData){
                seedFdcData();
            }

            LOG.info("db-seeding.seed-json-data: {}", seedFdcData);
            if (seedJsonData){
                try {
                    seedJsonData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void truncateTables() {
        userRepository.deleteAll();

        // food, nutrient, conversion_ratio
        foodRepository.deleteAll();

        // combo_food, combo_food_conversion_ratio, combo_food_constituent
        comboFoodRepository.deleteAll();

        // units
        unitRepository.deleteAll();
    }

    private void seedUser() {
        User user = new User();
        user.setUsername("username");
        seededUsers.add(userRepository.saveAndFlush(user));
    }

    private void seedUnits() {
        Unit unit = new Unit();
        unit.setUser(seededUsers.get(0));
        unit.setIsDraft(false);
        unit.setAbbreviation("ckr");
        unitRepository.saveAndFlush(unit);

        Unit draft = new Unit();
        draft.setUser(seededUsers.get(0));
        draft.setIsDraft(true);
        draft.setDraftOf(unit);
        draft.setSingular("cracker");
        draft.setPlural("crackers");
        draft.setAbbreviation("ckr");
        unitRepository.saveAndFlush(draft);

        Unit roughDraft = new Unit(); // draft but not of something else.
        roughDraft.setUser(seededUsers.get(0));
        roughDraft.setIsDraft(true);
        roughDraft.setSingular("box");
        roughDraft.setPlural("boxes");
        roughDraft.setAbbreviation("box");
        unitRepository.saveAndFlush(roughDraft);
    }

    private void seedFdcData() {
        // PBJ Food items
        FoodDTO peanutButterDTO = foodService.create(fdcService.getFoodByFdcId("358350"));
        FoodDTO jellyDTO = foodService.create(fdcService.getFoodByFdcId("386995"));
        FoodDTO breadDTO = foodService.create(fdcService.getFoodByFdcId("406269"));

//        // PBJ Combo Food
//        ComboFoodDTO pbjDTO = new ComboFoodDTO();
//        pbjDTO.setIsDraft(false);
//        pbjDTO.setDescription("Peanut Butter Jelly Sandwich");
//
//        // PBJ Combo Food - conversion ratios
//        List<ComboFoodConversionRatioDTO> conversionRatios = new ArrayList();
//        pbjDTO.setConversionRatios(conversionRatios);
//
//        ComboFoodConversionRatioDTO cfCvRat1 = new ComboFoodConversionRatioDTO();
//        cfCvRat1.setAmountA(new BigDecimal("1"));
//        cfCvRat1.setUnitA("Sandwich");
//        cfCvRat1.setAmountB(new BigDecimal("1"));
//        cfCvRat1.setUnitB("SERVING_SIZE_REF");
//        conversionRatios.add(cfCvRat1);
//
//        ComboFoodConversionRatioDTO cfCvRat2 = new ComboFoodConversionRatioDTO();
//        cfCvRat2.setAmountA(new BigDecimal("1"));
//        cfCvRat2.setUnitA("CONSTITUENTS_SIZE_REF");
//        cfCvRat2.setAmountB(new BigDecimal("1"));
//        cfCvRat2.setUnitB("SERVING_SIZE_REF");
//        conversionRatios.add(cfCvRat2);
//
//        // PBJ Combo Food - constituents
//        List<ComboFoodConstituentDTO> constituents = new ArrayList();
//        pbjDTO.setConstituents(constituents);
//
//        ComboFoodConstituentDTO peanutButterCst = new ComboFoodConstituentDTO();
//        peanutButterCst.setFood(peanutButterDTO);
//        peanutButterCst.setAmount(new BigDecimal("3"));
//        peanutButterCst.setUnit("Tbs");
//        constituents.add(peanutButterCst);
//
//        ComboFoodConstituentDTO jellyCst = new ComboFoodConstituentDTO();
//        jellyCst.setFood(jellyDTO);
//        jellyCst.setAmount(new BigDecimal("1"));
//        jellyCst.setUnit("Tbs");
//        constituents.add(jellyCst);
//
//        ComboFoodConstituentDTO breadCst = new ComboFoodConstituentDTO();
//        breadCst.setFood(breadDTO);
//        breadCst.setAmount(new BigDecimal("98")); // 2 slices
//        breadCst.setUnit("g");
//        constituents.add(breadCst);
//
//        comboFoodService.create(pbjDTO);
    }

    // seed from json files copied from seeding from fdc way
    private void seedJsonData() throws IOException {
        FoodDTO peanutButterDTO = objectMapper.readValue(peanutButterJson.getInputStream(), FoodDTO.class);
        foodService.create(peanutButterDTO);

        FoodDTO jellyDTO = objectMapper.readValue(jellyJson.getInputStream(), FoodDTO.class);
        foodService.create(jellyDTO);

        FoodDTO breadDTO = objectMapper.readValue(breadJson.getInputStream(), FoodDTO.class);
        foodService.create(breadDTO);
    }
}
