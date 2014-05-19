PRAGMA encoding = "UTF-8"; 
DROP TABLE IF EXISTS `grade` ;

-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grade` (
  `index` INT NOT NULL,
  `year` INT NOT NULL,
  `semester` TEXT NOT NULL,
  `number` TEXT NOT NULL,
  `code` TEXT NOT NULL,
  `section` TEXT NULL,
  `type` TEXT NOT NULL,
  `credit` INT NOT NULL,
  `au` INT NOT NULL,
  `grade` TEXT NOT NULL,
  PRIMARY KEY (`index`))
;