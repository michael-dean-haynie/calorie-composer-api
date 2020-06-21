package com.codetudes.caloriecomposerapi.db.repositories;

import com.codetudes.caloriecomposerapi.db.domain.ComboFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboFoodRepository extends JpaRepository<ComboFood, Long> {
}
