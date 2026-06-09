-- 1. 사용자 테이블 (user)
CREATE TABLE `user` (
    `userId` VARCHAR(50) PRIMARY KEY COMMENT '사용자 아이디 (고유 식별자)',
    `password` VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `name` VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    `role` VARCHAR(20) NOT NULL COMMENT '권한 ("TRAINER" 또는 "MEMBER")',
    `trainerId` VARCHAR(50) COMMENT '(회원 전용) 담당 트레이너 아이디',
    `ptCount` INT DEFAULT 0 COMMENT '(회원 전용) 잔여 PT 횟수'
);

-- 2. 식단 과제 테이블 (meal)
CREATE TABLE `meal` (
    `assignId` INT AUTO_INCREMENT PRIMARY KEY COMMENT '식단 과제 고유 식별 번호',
    `memberId` VARCHAR(50) NOT NULL COMMENT '목표를 부여받은 회원 아이디',
    `targetDate` VARCHAR(20) NOT NULL COMMENT '목표 날짜 (예: "2026-05-15")',
    `targetCalories` INT COMMENT '목표 총 섭취 칼로리',
    `targetCarbs` INT COMMENT '목표 탄수화물 (g)',
    `targetProtein` INT COMMENT '목표 단백질 (g)',
    `targetFat` INT COMMENT '목표 지방 (g)'
);

-- 3. 운동 과제 테이블 (exercise)
CREATE TABLE `exercise` (
    `assignId` INT AUTO_INCREMENT PRIMARY KEY COMMENT '운동 과제 고유 식별 번호',
    `memberId` VARCHAR(50) NOT NULL COMMENT '목표를 부여받은 회원 아이디',
    `targetDate` VARCHAR(20) NOT NULL COMMENT '목표 날짜 (예: "2026-05-15")',
    `targetExercise` VARCHAR(255) COMMENT '목표 운동 이름',
    `targetBurnedCal` INT COMMENT '목표 소비 칼로리',
    `todoGoal` BOOLEAN COMMENT '목표 달성 여부'
);

-- 4. 식단 기록 테이블 (record)
CREATE TABLE `record` (
    `recordId` INT AUTO_INCREMENT PRIMARY KEY COMMENT '식단 기록 고유 식별 번호',
    `memberId` VARCHAR(50) NOT NULL COMMENT '기록을 작성한 회원 아이디',
    `recordDate` VARCHAR(20) NOT NULL COMMENT '기록 날짜',
    `mealTime` VARCHAR(50) COMMENT '섭취 시간',
    `mealType` VARCHAR(20) COMMENT '식사 분류 (아침, 점심, 저녁, 간식)',
    `carbs` INT COMMENT '섭취 탄수화물 (g)',
    `protein` INT COMMENT '섭취 단백질 (g)',
    `fat` INT COMMENT '섭취 지방 (g)',
    `calories` INT COMMENT '섭취 칼로리'
);