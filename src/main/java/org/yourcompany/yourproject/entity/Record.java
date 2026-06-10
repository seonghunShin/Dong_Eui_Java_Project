package org.yourcompany.yourproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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