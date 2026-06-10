package org.yourcompany.yourproject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    // 캘린더 및 대시보드용
    @Query("SELECT r FROM Record r WHERE r.member.userId = :userId AND r.recordDate = :recordDate ORDER BY r.mealTime ASC")
    List<Record> findByMember_UserIdAndRecordDate(@Param("userId") String userId, @Param("recordDate") LocalDate recordDate);
}