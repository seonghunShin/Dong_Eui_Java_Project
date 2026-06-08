package org.yourcompany.yourproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor; // 필수 추가
import lombok.Builder;           // 필수 유지
import lombok.Getter;
import lombok.NoArgsConstructor;  // 필수 추가

@Getter
@Builder
@NoArgsConstructor  // 필수 추가
@AllArgsConstructor // 필수 추가
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) // 자동 증가 설정
    private Long recordId;
    @ManyToOne
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