PRAGMA encoding = "UTF-8"; 
DROP TABLE IF EXISTS `grade` ;
DROP TABLE IF EXISTS `metadata` ;

-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grade` (
  `unique` INTEGER PRIMARY KEY AUTOINCREMENT,
  `index` INT NULL,
  `year` INT NULL,
  `semester` TEXT NULL,
  `number` TEXT NOT NULL,
  `code` TEXT NOT NULL,
  `section` TEXT NULL,
  `type` TEXT NOT NULL,
  `credit` INT NOT NULL,
  `au` INT NOT NULL,
  `grade` TEXT NOT NULL DEFAULT 'S')
;

CREATE TABLE IF NOT EXISTS `metadata` (
  `index` TEXT NOT NULL,
  `value` TEXT NOT NULL,
  PRIMARY KEY (`index`))
;