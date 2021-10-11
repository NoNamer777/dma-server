USE `dmadb`;

SET FOREIGN_KEY_CHECKS = 0;

SET GROUP_CONCAT_MAX_LEN = 32768;

SET @tables = NULL;

SELECT GROUP_CONCAT('`', `table_name`, '`')
    INTO @tables FROM `information_schema`.`tables`
    WHERE `table_schema` = (SELECT DATABASE());

SELECT IFNULL(@tables, 'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);

PREPARE statement FROM @tables;

EXECUTE statement;

DEALLOCATE PREPARE statement;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS `spell` (
    `id` BINARY(16) NOT NULL,
    `name` VARCHAR(64) NOT NULL,
    `level` TINYINT(2) NOT NULL DEFAULT 0,
    `magic_school` ENUM (
        'Abjuration',
        'Conjuration',
        'Divination',
        'Enchantment',
        'Evocation',
        'Illusion',
        'Necromancy',
        'Transmutation'
    ) NOT NULL,
    `ritual` TINYINT(2) NOT NULL DEFAULT 0,
    `casting_time` VARCHAR(16) NOT NULL,
    `range` VARCHAR(16) NOT NULL,
    `concentration` TINYINT(2) NOT NULL DEFAULT 0,
    `duration` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `unique_spell_id` UNIQUE (`id`),
    CONSTRAINT `unique_spell_name` UNIQUE (`name`),
    CHECK (`level` >= 0 AND `level` <= 9)
);

CREATE UNIQUE INDEX `spell_idx` ON `spell` (`id`, `name`);

CREATE TABLE `spell_component` (
    `spell_id` BINARY(16) NOT NULL,
    `component` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`spell_id`, `component`),
    CONSTRAINT `spell_component_fk` FOREIGN KEY (`spell_id`) REFERENCES `spell`(`id`)
)
