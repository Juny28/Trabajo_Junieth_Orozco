-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema apifilm
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `apifilm` ;

-- -----------------------------------------------------
-- Schema apifilm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `apifilm` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `apifilm` ;

-- -----------------------------------------------------
-- Table `apifilm`.`films`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apifilm`.`films` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `apifilm`.`films` (
  `film_id` INT NOT NULL AUTO_INCREMENT,
  `film_name` VARCHAR(150) NOT NULL,
  `release_date` DATE NOT NULL,
  PRIMARY KEY (`film_id`))
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `film_name_UNIQUE` ON `apifilm`.`films` (`film_name` ASC) VISIBLE;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
