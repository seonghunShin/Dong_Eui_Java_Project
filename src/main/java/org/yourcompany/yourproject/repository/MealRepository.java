package org.yourcompany.yourproject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    /**
     * 의사코드: (memberId == id) and (targetDate == 오늘 날짜) 식단 조회 부분 대응
     * - 특정 회원의 '특정 날짜' 식단 목표(권장 칼로리/탄단지 가이드)를 가져옵니다.
     */
    Optional<Meal> findByMember_UserIdAndTargetDate(String userId, LocalDate targetDate);
}