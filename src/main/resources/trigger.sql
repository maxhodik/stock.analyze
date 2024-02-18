DROP TRIGGER IF EXISTS `stock_analyze`.`company_BEFORE_INSERT`;

DELIMITER $$
USE `stock_analyze`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `company_BEFORE_INSERT` BEFORE INSERT ON `company` FOR EACH ROW BEGIN
    IF EXISTS (SELECT 1 FROM company WHERE symbol = NEW.symbol) THEN
        SET NEW.symbol = NULL;
    END IF;
END$$
DELIMITER ;
