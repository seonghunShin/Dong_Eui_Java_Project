package org.yourcompany.yourproject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * 의사코드: (memberId == id) and (targetDate == 오늘 날짜) 운동 조회 부분 대응
     * - 특정 회원의 '특정 날짜' 운동 과제를 찾아 오늘 고유 코드(assignId) 및 달성 상태를 반환하도록 돕습니다.
     */
    Optional<Exercise> findByMember_UserIdAndTargetDate(String userId, LocalDate targetDate);
}