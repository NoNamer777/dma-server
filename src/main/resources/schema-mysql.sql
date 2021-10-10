USE `dmadb`;

DROP TABLE IF EXISTS `spell`;

CREATE TABLE IF NOT EXISTS `spell`
(
    `id`            BINARY(16)                                                                                                              NOT NULL,
    `name`          VARCHAR(64)                                                                                                             NOT NULL,
    `level`         TINYINT(2)                                                                                                              NOT NULL DEFAULT 0,
    `magic_school`  ENUM ('Abjuration', 'Conjuration', 'Divination', 'Enchantment', 'Evocation', 'Illusion', 'Necromancy', 'Transmutation') NOT NULL,
    `ritual`        TINYINT(2)                                                                                                              NOT NULL DEFAULT 0,
    `casting_time`  VARCHAR(16)                                                                                                             NOT NULL,
    `range`         VARCHAR(16)                                                                                                             NOT NULL,
    `concentration` TINYINT(2)                                                                                                              NOT NULL DEFAULT 0,
    `duration`      VARCHAR(16)                                                                                                             NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `unique_spell_id` UNIQUE (`id`),
    CONSTRAINT `unique_spell_name` UNIQUE (`name`),
    CHECK (`level` >= 0 AND `level` <= 9)
);

CREATE UNIQUE INDEX `spell_idx` ON `spell` (`id`, `name`);
