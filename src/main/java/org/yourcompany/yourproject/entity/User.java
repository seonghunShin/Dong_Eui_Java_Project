package org.yourcompany.yourproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;     
    private String password;   
    private String name;       
    private String role;       
    private String trainerId;  
    private int ptCount;       

    public void updatePtCount(int newPtCount) {
        if (newPtCount < 0) {
            throw new IllegalArgumentException("PT 횟수는 0보다 작을 수 없습니다.");
        }
        this.ptCount = newPtCount;
    }

    public void disconnectTrainer() {
        this.trainerId = null;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }
    
}