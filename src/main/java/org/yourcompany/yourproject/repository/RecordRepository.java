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

    /**
     * 의사코드: if(memberId == id) and (recordDate == 오늘 날짜) 대응 (캘린더 및 대시보드용)
     * - 회원이 '오늘 하루 동안' 입력한 모든 식단 기록들을 리스트 형태로 가져옵니다.
     * - 섭취 시간(mealTime) 순으로 정렬(ORDER BY)하여 화면 타임라인 배치를 이쁘게 만들어줍니다.
     */
    @Query("SELECT r FROM Record r WHERE r.member.userId = :userId AND r.recordDate = :recordDate ORDER BY r.mealTime ASC")
    List<Record> findByMember_UserIdAndRecordDate(@Param("userId") String userId, @Param("recordDate") LocalDate recordDate);
}