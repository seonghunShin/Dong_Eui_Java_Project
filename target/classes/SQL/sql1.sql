-- 1. 사용자 테이블 (JPA 매핑에 의해 user_id가 PK)
CREATE TABLE `users` (
    `user_id` VARCHAR(255) PRIMARY KEY,
    `password` VARCHAR(255),
    `name` VARCHAR(255),
    `role` VARCHAR(255),
    `trainer_id` VARCHAR(255),
    `pt_count` INT NOT NULL
);

-- 2. 식단 과제 테이블 (member_user_id로 매핑됨)
CREATE TABLE `meal` (
    `assign_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_user_id` VARCHAR(255), 
    `target_date` DATE,
    `target_calories` INT NOT NULL,
    `target_carbs` INT NOT NULL,
    `target_protein` INT NOT NULL,
    `target_fat` INT NOT NULL,
    FOREIGN KEY (`member_user_id`) REFERENCES `users`(`user_id`)
);

-- 3. 운동 과제 테이블
CREATE TABLE `exercise` (
    `assign_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_user_id` VARCHAR(255),
    `target_date` DATE,
    `target_exercise` VARCHAR(255),
    `target_burned_cal` INT NOT NULL,
    `todo_goal` BIT(1) NOT NULL,
    FOREIGN KEY (`member_user_id`) REFERENCES `users`(`user_id`)
);

-- 4. 식단 기록 테이블
CREATE TABLE `record` (
    `record_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_user_id` VARCHAR(255),
    `meal_type` VARCHAR(255),
    `meal_time` VARCHAR(255),
    `food_name` VARCHAR(255),
    `carbo` INT NOT NULL,
    `protein` INT NOT NULL,
    `fat` INT NOT NULL,
    `calories` INT NOT NULL,
    `record_date` DATE,
    `record_date_time` DATETIME(6),
    FOREIGN KEY (`member_user_id`) REFERENCES `users`(`user_id`)
);