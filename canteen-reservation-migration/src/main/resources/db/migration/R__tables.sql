SET NAMES utf8mb4;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
CREATE TABLE IF NOT EXISTS `admins`  (
    `id`            INT(11)         NOT NULL,
    `name`          VARCHAR(50)     NOT NULL,
    `password`      VARCHAR(50)     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_unique_index`(`name`)
);

-- ----------------------------
-- Table structure for email_notifications
-- ----------------------------
CREATE TABLE IF NOT EXISTS `email_notifications`  (
    `id`                INT(11) NOT NULL AUTO_INCREMENT,
    `user_email`        VARCHAR(50)             NOT NULL,
    `reservation_id`    VARCHAR(50)             NOT NULL,
    `notifying_time`    TIMESTAMP(6)            NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `sending_status`    VARCHAR(50)             NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `reservation_id_unique`(`reservation_id`)
);

-- ----------------------------
-- Table structure for reservation_meals
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reservation_meals`  (
    `id`                INT(11) NOT NULL AUTO_INCREMENT,
    `reservation_id`    VARCHAR(50)             NOT NULL,
    `meal_id`           VARCHAR(50)             NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_reservation_fk`(`reservation_id`),
    INDEX `meal_fk`(`meal_id`)
);

-- ----------------------------
-- Table structure for reservations
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reservations`  (
    `id`                    VARCHAR(50)             NOT NULL,
    `reserving_amount`      DOUBLE(11, 3)           NOT NULL,
    `reserving_time`        TIMESTAMP(6)            NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `status`                VARCHAR(50)             NOT NULL,
    `eating_time`           TIMESTAMP(6)            NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'time of user eating meal',
    `user_id`               INT(11)                 NOT NULL,
    `restaurant_id`         VARCHAR(200)            NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_fk`(`user_id`)
);

-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE IF NOT EXISTS `users`  (
    `id`                INT(11) NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(255),
    `email`             VARCHAR(20)                NOT NULL,
    `password`          VARCHAR(50)                NOT NULL,
    `status`            VARCHAR(50)                NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_index`(`email`)
);