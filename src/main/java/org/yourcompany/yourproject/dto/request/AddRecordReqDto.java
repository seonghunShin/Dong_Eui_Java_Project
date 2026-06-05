package org.yourcompany.yourproject.dto;

import java.time.LocalDateTime;

import org.yourcompany.yourproject.entity.Record;
import org.yourcompany.yourproject.entity.User;

import lombok.Data;

@Data
public class AddRecordReqDto {
    private String mealType;
    private String foodName;
    private int calories;
    private int carbo;
    private int protein;
    private int fat;

    public Record toEntity(User member) {
        return Record.builder()
                .member(member)
                .mealType(this.mealType)
                .foodName(this.foodName)
                .calories(this.calories)
                .carbo(this.carbo)
                .protein(this.protein)
                .fat(fat)
                .recordDateTime(LocalDateTime.now())
                .build();
    }
}