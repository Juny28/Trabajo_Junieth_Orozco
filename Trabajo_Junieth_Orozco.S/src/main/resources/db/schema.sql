DROP SCHEMA IF EXISTS `watchlist` ;
CREATE SCHEMA IF NOT EXISTS `watchlist` DEFAULT CHARACTER SET utf8mb4 ;
USE `watchlist` ;

CREATE TABLE IF NOT EXISTS `roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(90) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `titles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `watchmode_id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `year` INT NOT NULL,
  `genre` VARCHAR(100) NOT NULL,
  `poster` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `watchmode_id_UNIQUE` (`watchmode_id` ASC)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `reviews` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `rating` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  `user_id` BIGINT NOT NULL,
  `title_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_reviews_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_reviews_titles`
    FOREIGN KEY (`title_id`)
    REFERENCES `titles` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `user_favorites` (
  `user_id` BIGINT NOT NULL,
  `title_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `title_id`),
  CONSTRAINT `fk_favorites_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_favorites_titles`
    FOREIGN KEY (`title_id`)
    REFERENCES `titles` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Initial Roles
INSERT IGNORE INTO `roles` (`id`, `name`) VALUES (1, 'USER'), (2, 'ADMIN');