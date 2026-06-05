package org.yourcompany.yourproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 의사코드: 클레스[트레이너 담당 리스트 불러오기] 지원
     * - 특정 트레이너의 고유 ID를 기반으로 담당 회원 목록을 조회합니다.
     */
    List<User> findByTrainerId(String trainerId);

    // 회원가입 시 중복 아이디 체크를 위한 기능입니다.
    boolean existsByUserId(String userId);
}