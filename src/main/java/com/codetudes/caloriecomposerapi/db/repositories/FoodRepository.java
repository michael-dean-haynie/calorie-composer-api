package com.codetudes.caloriecomposerapi.db.repositories;

import com.codetudes.caloriecomposerapi.db.domain.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE f.description LIKE %:query% OR f.brandOwner LIKE %:query%")
    public List<Food> search(@Param("query") String query, Pageable pageable);
}
