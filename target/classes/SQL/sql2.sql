-- PHPMyAdmin 클릭 -> 화면 상단 "데이터베이스" 클릭 -> 
-- 데이터베이스명: javaproject  "새 데이터베이스 만들기" 클릭 -> javaproject 선택 후 SQL작성.

-- ==========================================
-- 1. 사용자 테이블 (user)
-- ==========================================
INSERT INTO `users` (`user_id`, `password`, `name`, `role`, `trainer_id`, `pt_count`) VALUES
('trainer1', '1234', '정지원', 'TRAINER', NULL, 0),
('member1', '1234', '서준영', 'MEMBER', 'trainer1', 12),
('member2', '1234', '유재희', 'MEMBER', 'trainer1', 5),
('member3', '1234', '신성훈', 'MEMBER', 'trainer1', 8);

-- ==========================================
-- 2. 식단 과제 테이블 (meal) 더미 데이터
-- ==========================================
-- memberId 대신 JPA 외래 키 관례인 `member_user_id`로 변경했습니다.
INSERT INTO `meal` (`member_user_id`, `target_date`, `target_calories`, `target_carbs`, `target_protein`, `target_fat`) VALUES
('member1', '2026-05-21', 2500, 300, 150, 70),
('member2', '2026-05-21', 2000, 200, 120, 50),
('member1', '2026-05-22', 2500, 300, 150, 70);

-- ==========================================
-- 3. 운동 과제 테이블 (exercise) 더미 데이터
-- ==========================================
-- memberId -> `member_user_id`, targetBurnedCal -> `target_burned_cal` 등으로 변경했습니다.
INSERT INTO `exercise` (`member_user_id`, `target_date`, `target_exercise`, `target_burned_cal`, `todo_goal`) VALUES
('member1', '2026-05-21', '스쿼트 5세트, 벤치프레스 5세트', 500, FALSE),
('member2', '2026-05-21', '러닝머신 인터벌 40분', 350, FALSE),
('member1', '2026-05-22', '데드리프트 5세트, 턱걸이 50개', 600, FALSE);

-- ==========================================
-- 4. 식단 기록 테이블 (record) 더미 데이터
-- ==========================================
-- record_date, meal_time, meal_type 등 모든 컬럼을 snake_case로 변경했습니다.
INSERT INTO `record` (`member_user_id`, `record_date`, `meal_time`, `meal_type`, `carbo`, `protein`, `fat`, `calories`) VALUES
('member1', '2026-05-21', '08:30', '아침', 80, 30, 15, 600),
('member1', '2026-05-21', '12:40', '점심', 100, 45, 20, 850),
('member1', '2026-05-21', '18:10', '저녁', 70, 40, 15, 550),
('member2', '2026-05-21', '09:00', '아침', 60, 20, 10, 400),
('member2', '2026-05-21', '13:00', '점심', 80, 30, 15, 600);