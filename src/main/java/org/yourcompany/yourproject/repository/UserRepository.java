package org.yourcompany.yourproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yourcompany.yourproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 트레이너 담당 회원 목록 조회
    List<User> findByTrainerId(String trainerId);

    // 회원가입 시 중복 아이디 체크
    boolean existsByUserId(String userId);
}