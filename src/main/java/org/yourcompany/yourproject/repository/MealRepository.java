package org.yourcompany.yourproject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    // 식단 조회
    Optional<Meal> findByMember_UserIdAndTargetDate(String userId, LocalDate targetDate);
}