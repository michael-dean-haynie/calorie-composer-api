package com.codetudes.caloriecomposerapi.db.repositories;

import com.codetudes.caloriecomposerapi.db.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
