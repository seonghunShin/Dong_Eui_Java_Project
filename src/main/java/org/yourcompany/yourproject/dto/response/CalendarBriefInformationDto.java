package org.yourcompany.yourproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarBriefInformationDto {
    private String todoGoal;             // 운동 완료 여부 문자열
    private int targetBurningCalories;   // 그날 설정된 목표 소모 칼로리
    private String recordDiet;           // 그날의 식단 요약 텍스트
}