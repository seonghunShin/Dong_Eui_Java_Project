package org.yourcompany.yourproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor; // 필수 추가
import lombok.Builder;           // 필수 유지
import lombok.Getter;
import lombok.NoArgsConstructor;  // 필수 추가

@Getter
@Builder
@NoArgsConstructor  // 필수 추가
@AllArgsConstructor // 필수 추가
public class Record {

    private Long recordId;                 
    private User member;                   
    private String mealType;               
    private String mealTime;               
    private String foodName;               
    private int carbo;                     
    private int protein;                   
    private int fat;                       
    private int calories;                  
    private LocalDate recordDate;          
    private LocalDateTime recordDateTime;  
}