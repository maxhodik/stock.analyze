ALTER TABLE `stock_analyze`.`stock`
CHANGE COLUMN `latest_price` `latest_price` DECIMAL(38,2) NULL DEFAULT NULL AFTER `company_name`;

DROP TRIGGER IF EXISTS `stock_analyze`.`stock_BEFORE_INSERT`;
DELIMITER $$
USE `stock_analyze`$$
CREATE DEFINER = CURRENT_USER TRIGGER `stock_analyze`.`stock_BEFORE_INSERT` BEFORE INSERT ON `stock` FOR EACH ROW
BEGIN
    INSERT INTO `stock_analyze` (delta) VALUES (NULL);
    INSERT INTO `stock_audit_log` (symbol, old_price, new_price, dml_type, `timestamp`)
    VALUES (NEW.symbol, NULL, NEW.latest_price, 'INSERT', NOW());
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `stock_analyze`.`stock_BEFORE_UPDATE`;
DELIMITER $$
USE `stock_analyze`$$
CREATE DEFINER = CURRENT_USER TRIGGER `stock_analyze`.`stock_BEFORE_UPDATE` BEFORE UPDATE ON `stock` FOR EACH ROW
BEGIN
    UPDATE `stock_analyze` SET delta = NEW.latest_price - OLD.latest_price;
    INSERT INTO `stock_audit_log` (symbol, old_price, new_price, dml_type, `timestamp`)
    VALUES (NEW.symbol, OLD.latest_price, NEW.latest_price, 'UPDATE', NOW());
END$$
DELIMITER ;
