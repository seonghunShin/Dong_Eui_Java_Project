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
@NoArgsConstructor  // 1. JPA 및 기본 생성자 인식을 위해 필수 추가
@AllArgsConstructor // 2. 빌더 패턴이 내부적으로 모든 필드 생성자를 쓸 수 있도록 필수 추가
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

    // 아래 정의하신 비즈니스 메서드는 그대로 유지하시면 됩니다.
    public void updatePtCount(int newPtCount) {
        if (newPtCount < 0) {
            throw new IllegalArgumentException("PT 횟수는 0보다 작을 수 없습니다.");
        }
        this.ptCount = newPtCount;
    }

    public void disconnectTrainer() {
        this.trainerId = null;
    }
}