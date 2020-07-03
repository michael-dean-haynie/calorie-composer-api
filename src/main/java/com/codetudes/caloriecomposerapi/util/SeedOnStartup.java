package com.codetudes.caloriecomposerapi.util;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.ComboFoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

        // combo_food, combo_food_food_amount, combo_food_portion
        comboFoodRepository.deleteAll();
    }

    private void seed() {
        // first create user
        User user = new User();
        user.setUsername("username");
        userRepository.save(user);

        // PBJ
        FoodDTO peanutButterDTO = foodService.create(fdcService.getFoodByFdcId("358350"));
        FoodDTO jelleyDTO = foodService.create(fdcService.getFoodByFdcId("386995"));
        FoodDTO breadDTO = foodService.create(fdcService.getFoodByFdcId("406269"));
    }
}
