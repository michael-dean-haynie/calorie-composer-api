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

    // Select food items that either (are not drafts) or (are a draft, but are standalone - not a draftOf anything)
    // For getting a list of foods to display on management page, where users can see if they are still being drafted or complete or have a working draft
    @Query("SELECT f, f.draft, f.draftOf FROM Food f " +
            "LEFT JOIN f.draft " +
            "LEFT JOIN f.draftOf " +
            "WHERE " +
                "((f.isDraft IS NULL OR f.isDraft = false)) " +
                "OR (f.isDraft = true AND f.draftOf IS NULL)"
    )
    public List<Food> findAllFoods();

    // Select food items that either (are not drafts, but have a draft) or (are a draft, but are standalone - not a draftOf anything)
    // For getting a list of foods that are in the drafting process.
    @Query("SELECT f, f.draft, f.draftOf FROM Food f " +
            "LEFT JOIN f.draft " +
            "LEFT JOIN f.draftOf " +
            "WHERE " +
                "((f.isDraft IS NULL OR f.isDraft = false) AND f.draft.id IS NOT NULL) " +
                "OR (f.isDraft = true AND f.draftOf IS NULL)"
    )
    public List<Food> findDrafts();
}
