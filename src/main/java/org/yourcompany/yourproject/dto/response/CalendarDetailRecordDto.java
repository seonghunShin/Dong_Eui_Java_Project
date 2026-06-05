package org.yourcompany.yourproject.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarDetailRecordDto {
    private Long recordId;       // 식단 기록 고유 번호
    private String memberId;     // 회원 ID
    private LocalDate recordDate;// 기록 날짜 (DATE 타입 변경 반영)
    private String mealTime;     // 섭취 시간 (예: "08:30", "12:40")
    private String mealType;     // 식사 분류 (아침, 점심, 저녁, 간식)
    private String foodName;     // 음식명 (예: "닭가슴살 샐러드")
    private int carbs;           // 섭취 탄수화물
    private int protein;         // 섭취 단백질
    private int fat;             // 섭취 지방
    private int calories;        // 섭취 칼로리
}