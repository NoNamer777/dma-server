USE `dmadb`;

CREATE TABLE IF NOT EXISTS `user` (
    `id` VARCHAR(64),
    `username` VARCHAR(64) NOT NULL,
    CONSTRAINT `user_pk` PRIMARY KEY (`id`),
    CONSTRAINT `unique_username` UNIQUE (`username`),
    CONSTRAINT `user_idx` UNIQUE INDEX (`id`, `username`)
);

CREATE TABLE `user_role` (
    `user_id` VARCHAR(64),
    `role` VARCHAR(16),
    CONSTRAINT `user_role_pk` PRIMARY KEY (`user_id`, `role`),
    CONSTRAINT `user_role_fk` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- -----------------------------------------------------
-- Users
-- -----------------------------------------------------
INSERT INTO `user` (`id`, `username`) VALUES
    ('a46183ba-d63c-4207-9e87-435f6f6b284b', 'simple_user'),
    ('30dc8b20-a87a-42a0-bdfb-c86a61a3b0d7', 'dungeon_master'),
    ('7892fda9-89c3-410e-8341-bb10a3fa0adf', 'admin');

-- -----------------------------------------------------
-- Users
-- -----------------------------------------------------
INSERT INTO `user_role` (`user_id`, `role`) VALUES
    ('30dc8b20-a87a-42a0-bdfb-c86a61a3b0d7', 'Dungeon Master'),
    ('7892fda9-89c3-410e-8341-bb10a3fa0adf', 'Admin');
