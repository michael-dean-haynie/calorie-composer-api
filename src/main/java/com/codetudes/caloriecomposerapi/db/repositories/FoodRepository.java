package com.codetudes.caloriecomposerapi.db.repositories;

import com.codetudes.caloriecomposerapi.db.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
