-- Pet adoption system initialization script
-- Database: MySQL 5.7 / 8.0
-- Usage: run directly in Navicat or any MySQL client

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `db_pet_manage_adopt`
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE `db_pet_manage_adopt`;

DROP TABLE IF EXISTS `operation_logs`;
DROP TABLE IF EXISTS `adopt_records`;
DROP TABLE IF EXISTS `pets`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `username` VARCHAR(50) NOT NULL COMMENT 'username',
    `password` VARCHAR(100) NOT NULL COMMENT 'password',
    `phone` VARCHAR(20) NOT NULL COMMENT 'phone',
    `role` VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT 'user role',
    `status` VARCHAR(20) NOT NULL DEFAULT 'ENABLED' COMMENT 'account status',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_username` (`username`),
    UNIQUE KEY `uk_users_phone` (`phone`),
    KEY `idx_users_role` (`role`),
    KEY `idx_users_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='users table';

CREATE TABLE `pets` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'pet id',
    `pet_name` VARCHAR(50) NOT NULL COMMENT 'pet name',
    `type` VARCHAR(20) NOT NULL DEFAULT 'other' COMMENT 'pet type',
    `age` INT NOT NULL DEFAULT 0 COMMENT 'pet age',
    `gender` VARCHAR(20) DEFAULT 'unknown' COMMENT 'pet gender',
    `image_url` VARCHAR(255) DEFAULT NULL COMMENT 'image url',
    `description` VARCHAR(500) DEFAULT NULL COMMENT 'description',
    `review_status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'review status',
    `adopt_status` VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT 'adoption status',
    `reviewer_id` BIGINT DEFAULT NULL COMMENT 'reviewer id',
    `review_remark` VARCHAR(255) DEFAULT NULL COMMENT 'review remark',
    `reviewed_at` DATETIME DEFAULT NULL COMMENT 'review time',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    KEY `idx_pets_type` (`type`),
    KEY `idx_pets_review_status` (`review_status`),
    KEY `idx_pets_adopt_status` (`adopt_status`),
    KEY `idx_pets_reviewer_id` (`reviewer_id`),
    CONSTRAINT `fk_pets_reviewer` FOREIGN KEY (`reviewer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='pets table';

CREATE TABLE `adopt_records` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'adoption record id',
    `user_id` BIGINT NOT NULL COMMENT 'user id',
    `pet_id` BIGINT NOT NULL COMMENT 'pet id',
    `apply_description` VARCHAR(500) NOT NULL COMMENT 'application description',
    `status` VARCHAR(20) NOT NULL DEFAULT 'WAITING' COMMENT 'application status',
    `audit_remark` VARCHAR(255) DEFAULT NULL COMMENT 'audit remark',
    `audited_by` BIGINT DEFAULT NULL COMMENT 'auditor id',
    `audited_at` DATETIME DEFAULT NULL COMMENT 'audit time',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_adopt_user_pet` (`user_id`, `pet_id`),
    KEY `idx_adopt_pet_id` (`pet_id`),
    KEY `idx_adopt_status` (`status`),
    KEY `idx_adopt_audited_by` (`audited_by`),
    CONSTRAINT `fk_adopt_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_adopt_pet` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`),
    CONSTRAINT `fk_adopt_audited_by` FOREIGN KEY (`audited_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='adoption records table';

CREATE TABLE `operation_logs` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'log id',
    `operator_id` BIGINT DEFAULT NULL COMMENT 'operator id',
    `operator_role` VARCHAR(20) DEFAULT NULL COMMENT 'operator role',
    `module` VARCHAR(50) NOT NULL COMMENT 'module',
    `action` VARCHAR(50) NOT NULL COMMENT 'action',
    `target_id` BIGINT DEFAULT NULL COMMENT 'target id',
    `content` VARCHAR(500) DEFAULT NULL COMMENT 'content',
    `ip` VARCHAR(50) DEFAULT NULL COMMENT 'request ip',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    PRIMARY KEY (`id`),
    KEY `idx_logs_operator_id` (`operator_id`),
    KEY `idx_logs_module` (`module`),
    KEY `idx_logs_action` (`action`),
    CONSTRAINT `fk_logs_operator` FOREIGN KEY (`operator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='operation logs table';

INSERT INTO `users` (`id`, `username`, `password`, `phone`, `role`, `status`) VALUES
(1, 'admin', 'admin123', '13800000000', 'admin', 'ENABLED'),
(2, 'user01', '123456', '13900000001', 'user', 'ENABLED'),
(3, 'user02', '123456', '13900000002', 'user', 'DISABLED');

INSERT INTO `pets` (`id`, `pet_name`, `type`, `age`, `gender`, `image_url`, `description`, `review_status`, `adopt_status`, `reviewer_id`, `review_remark`, `reviewed_at`) VALUES
(1, 'XiaoBai', 'dog', 2, 'male', '/images/pets/xiaobai.jpg', 'Friendly dog with basic medical check completed.', 'APPROVED', 'AVAILABLE', 1, 'Approved for display', NOW()),
(2, 'HuaHua', 'cat', 1, 'female', '/images/pets/huahua.jpg', 'Active cat suitable for family adoption.', 'APPROVED', 'AVAILABLE', 1, 'Approved for display', NOW()),
(3, 'HuiHui', 'other', 3, 'unknown', '/images/pets/huihui.jpg', 'Waiting for more profile details.', 'PENDING', 'AVAILABLE', NULL, NULL, NULL);

INSERT INTO `adopt_records` (`id`, `user_id`, `pet_id`, `apply_description`, `status`, `audit_remark`, `audited_by`, `audited_at`) VALUES
(1, 2, 1, 'I have a stable home and can take long-term care of this pet.', 'WAITING', NULL, NULL, NULL);

INSERT INTO `operation_logs` (`id`, `operator_id`, `operator_role`, `module`, `action`, `target_id`, `content`, `ip`) VALUES
(1, 1, 'admin', 'user', 'LOGIN', 1, 'Admin logged into the system', '127.0.0.1'),
(2, 1, 'admin', 'pet', 'REVIEW', 1, 'Pet profile approved', '127.0.0.1');

SET FOREIGN_KEY_CHECKS = 1;
