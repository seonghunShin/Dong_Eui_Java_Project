package org.yourcompany.yourproject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // 운동 조회
    Optional<Exercise> findByMember_UserIdAndTargetDate(String userId, LocalDate targetDate);
}