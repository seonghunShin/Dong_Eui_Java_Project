package org.yourcompany.yourproject.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarBriefInformationDto {
    private String todoGoal;             // 운동 완료 여부 문자열 (예: "성공", "진행중" 또는 "O", "X")
    private int targetBurningCalories;   // 그날 설정된 목표 소모 칼로리
    private String recordDiet;           // 그날의 식단 요약 텍스트 (예: "칼로리 달성" 또는 "초과/미달")
}