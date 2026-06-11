-- PHPMyAdmin 클릭 -> 화면 상단 "데이터베이스" 클릭 -> 
-- 데이터베이스명: javaproject  "새 데이터베이스 만들기" 클릭 -> javaproject 선택 후 SQL작성.

-- 1. 사용자 테이블 (user)
INSERT INTO `users` (`user_id`, `password`, `name`, `role`, `trainer_id`, `pt_count`) VALUES
('member1', '1234', '서준영', 'MEMBER', 'trainer1', 12),
('member2', '1234', '유재희', 'MEMBER', 'trainer1', 5),
('member3', '1234', '신성훈', 'MEMBER', 'trainer1', 10),
('trainer1', '1234', '정지원', 'TRAINER', NULL, 0);

-- 2. 식단 과제 테이블 (meal) 더미 데이터
INSERT INTO `meal` (`member_user_id`, `target_date`, `target_calories`, `target_carbs`, `target_protein`, `target_fat`) VALUES
('member1', '2026-05-21', 2500, 300, 150, 70),
('member2', '2026-05-21', 2000, 200, 120, 50),
('member1', '2026-05-22', 2500, 300, 150, 70),
('member1', '2026-06-10', 500, 100, 200, 50);

-- 3. 운동 과제 테이블 (exercise) 더미 데이터
INSERT INTO `exercise` (`member_user_id`, `target_date`, `target_exercise`, `target_burned_cal`, `todo_goal`) VALUES
('member1', '2026-05-21', '스쿼트 5세트, 벤치프레스 5세트', 500, 0),
('member2', '2026-05-21', '러닝머신 인터벌 40분', 350, 0),
('member1', '2026-05-22', '데드리프트 5세트, 턱걸이 50개', 600, 0),
('member1', '2026-06-10', '윗몸일으키기', 1000, 1);

-- 4. 식단 기록 테이블 (record) 더미 데이터.
INSERT INTO `record` (`member_user_id`, `record_date`, `meal_time`, `meal_type`, `food_name`, `carbo`, `protein`, `fat`, `calories`, `record_date_time`) VALUES
('member1', '2026-05-21', '08:30', '아침', NULL, 80, 30, 15, 600, NULL),
('member1', '2026-05-21', '12:40', '점심', NULL, 100, 45, 20, 850, NULL),
('member1', '2026-05-21', '18:10', '저녁', NULL, 70, 40, 15, 550, NULL),
('member2', '2026-05-21', '09:00', '아침', NULL, 60, 20, 10, 400, NULL),
('member2', '2026-05-21', '13:00', '점심', NULL, 80, 30, 15, 600, NULL),
('member1', '2026-06-10', '07:00', '아침', '닭가슴살', 1, 2, 1, 14, '2026-06-10 02:02:59'),
('member1', '2026-06-10', '10:03', '점심', '닭가슴살', 14, 14, 14, 12, '2026-06-10 02:03:20'),
('member1', '2026-06-10', '19:20', '저녁', '밥', 100, 200, 50, 474, '2026-06-10 07:30:08');