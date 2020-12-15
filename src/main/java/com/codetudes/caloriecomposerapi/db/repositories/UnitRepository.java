package com.codetudes.caloriecomposerapi.db.repositories;

import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findFirstByUserAndAbbreviationAndDraftOfIsNull(User user, String abbreviation);

    List<Unit> findByUserAndDraftOfIsNull(User user);

    Long countByIsDraftTrue();
}
