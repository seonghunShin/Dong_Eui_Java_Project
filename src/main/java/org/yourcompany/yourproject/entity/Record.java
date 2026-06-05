package org.yourcompany.yourproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Record {

    private Long recordId;                 // 식단 기록 고유 식별 번호 (PK, Auto_Increment)
    private User member;                   // 기록을 작성한 회원 객체 (외래키 매핑)
    private String mealType;               // 식사 분류 (아침, 점심, 저녁, 간식)
    private String mealTime;               // 섭취 시간 (예: "12:40", "18:10") -> 기획서 UI 대응
    private String foodName;               // 음식명 (예: "닭가슴살 샐러드")
    private int carbo;                     // 섭취 탄수화물 (g) -> 자바 명칭 통일화
    private int protein;                   // 섭취 단백질 (g)
    private int fat;                       // 섭취 지방 (g)
    private int calories;                  // 섭취 칼로리 (kcal)
    private LocalDate recordDate;          // 일별 데이터 고속 조회용 날짜 (DATE 타입 매핑)
    private LocalDateTime recordDateTime;  // 데이터 생성 타임스탬프 (DATETIME 타입 매핑)
}