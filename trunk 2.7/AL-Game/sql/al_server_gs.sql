-- ----------------------------
-- Table structure for `server_variables`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `server_variables` (
  `key` varchar(30) NOT NULL,
  `value` varchar(30) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure `for players`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `players` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `account_id` int(11) NOT NULL,
  `account_name` varchar(50) NOT NULL,
  `exp` bigint(20) NOT NULL default '0',
  `recoverexp` bigint(20) NOT NULL default '0',
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `heading` int(11) NOT NULL,
  `world_id` int(11) NOT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `race` enum('ASMODIANS','ELYOS') NOT NULL,
  `player_class` enum('WARRIOR','GLADIATOR','TEMPLAR','SCOUT','ASSASSIN','RANGER','MAGE','SORCERER','SPIRIT_MASTER','PRIEST','CLERIC','CHANTER') NOT NULL,
  `creation_date` timestamp NULL default NULL,
  `deletion_date` timestamp NULL default NULL,
  `last_online` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `quest_expands` tinyint(1) NOT NULL default '0',
  `npc_expands` tinyint(1) NOT NULL default '0',
  `advenced_stigma_slot_size` TINYINT(1) NOT NULL DEFAULT '0',
  `warehouse_size` tinyint(1) NOT NULL default '0',
  `mailbox_letters` tinyint(4) NOT NULL default '0',
  `bind_point` INT NOT NULL default '0',
  `title_id` int(3) NOT NULL default '-1',
  `dp` int(3) NOT NULL DEFAULT '0',
  `soul_sickness` tinyint(1) UNSIGNED NOT NULL DEFAULT '0',
  `reposte_energy` bigint(20) NOT NULL default '0',
  `online` tinyint(1) NOT NULL default '0',
  `note` text,
  `mentor_flag_time` INT(11) NOT NULL DEFAULT '0',
  `last_transfer_time` decimal(20) NOT NULL default '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`),
  INDEX (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_appearance`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `player_appearance` (
  `player_id` int(11) NOT NULL,
  `face` int(11) NOT NULL,
  `hair` int(11) NOT NULL,
  `deco` int(11) NOT NULL,
  `tattoo` int(11) NOT NULL,
  `face_contour` INT(11) NOT NULL,
  `expression` INT(11) NOT NULL,
  `jaw_line` INT(11) NOT NULL,
  `skin_rgb` int(11) NOT NULL,
  `hair_rgb` int(11) NOT NULL,
  `lip_rgb` int(11) NOT NULL,
  `eye_rgb` int(11) NOT NULL,
  `face_shape` int(11) NOT NULL,
  `forehead` int(11) NOT NULL,
  `eye_height` int(11) NOT NULL,
  `eye_space` int(11) NOT NULL,
  `eye_width` int(11) NOT NULL,
  `eye_size` int(11) NOT NULL,
  `eye_shape` int(11) NOT NULL,
  `eye_angle` int(11) NOT NULL,
  `brow_height` int(11) NOT NULL,
  `brow_angle` int(11) NOT NULL,
  `brow_shape` int(11) NOT NULL,
  `nose` int(11) NOT NULL,
  `nose_bridge` int(11) NOT NULL,
  `nose_width` int(11) NOT NULL,
  `nose_tip` int(11) NOT NULL,
  `cheek` int(11) NOT NULL,
  `lip_height` int(11) NOT NULL,
  `mouth_size` int(11) NOT NULL,
  `lip_size` int(11) NOT NULL,
  `smile` int(11) NOT NULL,
  `lip_shape` int(11) NOT NULL,
  `jaw_height` int(11) NOT NULL,
  `chin_jut` int(11) NOT NULL,
  `ear_shape` int(11) NOT NULL,
  `head_size` int(11) NOT NULL,
  `neck` int(11) NOT NULL,
  `neck_length` int(11) NOT NULL,
  `shoulders` int(11) NOT NULL,
  `shoulder_size` int(11) NOT NULL,
  `torso` int(11) NOT NULL,
  `chest` int(11) NOT NULL,
  `waist` int(11) NOT NULL,
  `hips` int(11) NOT NULL,
  `arm_thickness` int(11) NOT NULL,
  `arm_length` int(11) NOT NULL,
  `hand_size` int(11) NOT NULL,
  `leg_thickness` int(11) NOT NULL,
  `leg_length` int(11) NOT NULL,
  `foot_size` int(11) NOT NULL,
  `facial_rate` int(11) NOT NULL,
  `voice` int(11) NOT NULL,
  `height` float NOT NULL,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_macrosses`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `player_macrosses` (
  `player_id` int(11) NOT NULL,
  `order` int(3) NOT NULL,
  `macro` text NOT NULL,
  UNIQUE KEY `main` (`player_id`,`order`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_titles`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_titles` (
  `player_id` int(11) NOT NULL,
  `title_id` int(11) NOT NULL,
  `remaining` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`player_id`,`title_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `friends`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `friends` (
  `player` int(11) NOT NULL,
  `friend` int(11) NOT NULL,
  PRIMARY KEY (`player`,`friend`),
  FOREIGN KEY (`player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`friend`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `blocks`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `blocks` (
  `player` int(11) NOT NULL,
  `blocked_player` int(11) NOT NULL,
  `reason` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`player`,`blocked_player`),
  FOREIGN KEY (`player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`blocked_player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_settings`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `player_settings` (
  `player_id` int(11) NOT NULL,
  `settings_type` tinyint(1) NOT NULL,
  `settings` BLOB NOT NULL,
  PRIMARY KEY (`player_id`, `settings_type`),
  CONSTRAINT `ps_pl_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_skills`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `player_skills` (
  `player_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_level` int(3) NOT NULL default '1',
  PRIMARY KEY (`player_id`,`skill_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `inventory`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `inventory` (
  `item_unique_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` bigint(20) NOT NULL DEFAULT '0',
  `item_color` int(11) NOT NULL DEFAULT '0',
  `item_creator` varchar(50),
  `expire_time` int(11) NOT NULL DEFAULT '0',
  `activation_count` int(11) NOT NULL DEFAULT '0',
  `item_owner` int(11) NOT NULL,
  `is_equiped` TINYINT(1) NOT NULL DEFAULT '0',
  `is_soul_bound` TINYINT(1) NOT NULL DEFAULT '0', 
  `slot` INT NOT NULL DEFAULT '0',
  `item_location` TINYINT(1) DEFAULT '0',
  `enchant` TINYINT(1) DEFAULT '0',
  `item_skin`  int(11) NOT NULL DEFAULT 0,
  `fusioned_item` INT(11) NOT NULL DEFAULT '0',
  `optional_socket` INT(1) NOT NULL DEFAULT '0',
  `optional_fusion_socket` INT(1) NOT NULL DEFAULT '0',
  `charge` MEDIUMINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_unique_id`),
  KEY `item_owner`(`item_owner`),
  KEY `item_location`(`item_location`),
  KEY `is_equiped`(`is_equiped`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `item_stones`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `item_stones` (
  `item_unique_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `slot` int(2) NOT NULL,
  `category` int(2) NOT NULL default 0,
  PRIMARY KEY (`item_unique_id`, `slot`, `category`),
  FOREIGN KEY (`item_unique_id`) references inventory (`item_unique_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_quests`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `player_quests` (
  `player_id` int(11) NOT NULL,
  `quest_id` int(10) unsigned NOT NULL default '0',
  `status` varchar(10) NOT NULL default 'NONE',
  `quest_vars` int(10) unsigned NOT NULL default '0',
  `complete_count` int(3) unsigned NOT NULL default '0',
  `next_repeat_time` timestamp NULL default NULL,
  `reward`  smallint(3) NULL,
  `complete_time` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`player_id`,`quest_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_npc_factions`
-- ----------------------------
CREATE TABLE IF NOT EXISTS  `player_npc_factions` (
  `player_id` int(11) NOT NULL,
  `faction_id` int(2) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `time` int(11) NOT NULL,
  `state` enum('NOTING','START','COMPLETE') NOT NULL default 'NOTING',
  `quest_id` int(6) NOT NULL default '0',
  PRIMARY KEY  (`player_id`,`faction_id`),
  CONSTRAINT `player_npc_factions_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `abyss_rank`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `abyss_rank` (
  `player_id` int(11) NOT NULL,
  `daily_ap`  int(11) NOT NULL,
  `weekly_ap`  int(11) NOT NULL,
  `ap` int(11) NOT NULL,
  `rank` int(2) NOT NULL default '1',
  `top_ranking`  int(4) NOT NULL,
  `daily_kill`  int(5) NOT NULL,
  `weekly_kill`  int(5) NOT NULL,
  `all_kill` int(4) NOT NULL default '0',
  `max_rank` int(2) NOT NULL default '1',
  `last_kill`  int(5) NOT NULL,
  `last_ap`  int(11) NOT NULL,
  `last_update`  decimal(20,0) NOT NULL,
  `rank_pos` INT(11) NOT NULL DEFAULT '0' ,
  `old_rank_pos` INT(11) NOT NULL DEFAULT '0',
  `rank_ap` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`player_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `legions`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `legions` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `level` int(1) NOT NULL DEFAULT '1',
  `contribution_points` INT NOT NULL DEFAULT '0',
  `deputy_permission` INT(11) NOT NULL DEFAULT 7692,
  `centurion_permission` INT(11) NOT NULL DEFAULT 7176,
  `legionary_permission` INT(11) NOT NULL DEFAULT 6144,
  `volunteer_permission` INT(11) NOT NULL DEFAULT 2048,
  `disband_time` int(11) NOT NULL default '0',
  `rank_cp` INT(11) NOT NULL DEFAULT '0',
  `rank_pos` INT(11) NOT NULL DEFAULT '0',
  `old_rank_pos` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `legion_announcement_list` (
  `legion_id` int(11) NOT NULL,
  `announcement` varchar(256) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `legion_members` (
  `legion_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `nickname` varchar(10) NOT NULL default '',  
  `rank` enum( 'BRIGADE_GENERAL','CENTURION','LEGIONARY','DEPUTY','VOLUNTEER' ) NOT NULL DEFAULT 'VOLUNTEER',
  `selfintro` varchar(32) default '',
  PRIMARY KEY (`player_id`),
  KEY `player_id`(`player_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `legion_emblems` (
  `legion_id` int(11) NOT NULL,
  `emblem_id` int(1) NOT NULL default '0',
  `color_r` int(3) NOT NULL default '0',  
  `color_g` int(3) NOT NULL default '0', 
  `color_b` int(3) NOT NULL default '0',
  `emblem_type` enum('DEFAULT', 'CUSTOM') NOT NULL DEFAULT 'DEFAULT',
  `emblem_data` longblob,
  PRIMARY KEY (`legion_id`),
  FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `legion_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `legion_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `history_type` enum('CREATE','JOIN','KICK','APPOINTED','EMBLEM_REGISTER','EMBLEM_MODIFIED') NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_recipes`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_recipes` (
  `player_id` int(11) NOT NULL,
  `recipe_id` int(11) NOT NULL,
  PRIMARY KEY (`player_id`,`recipe_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_punisments`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_punishments` (
  `player_id` int(11) NOT NULL,
  `punishment_type` enum('PRISON','GATHER','CHARBAN') NOT NULL,
  `start_time` int(10) unsigned default '0',
  `duration` int(10) unsigned default '0',
  `reason` text,
  PRIMARY KEY  (`player_id`,`punishment_type`),
  CONSTRAINT `player_punishments_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `mail_table`
-- ----------------------------
CREATE TABLE IF NOT EXISTS  `mail` (
  `mail_unique_id` int(11) NOT NULL,
  `mail_recipient_id` int(11) NOT NULL,
  `sender_name` varchar(16) character set utf8 NOT NULL,
  `mail_title` varchar(20) character set utf8 NOT NULL,
  `mail_message` varchar(1000) character set utf8 NOT NULL,
  `unread` tinyint(4) NOT NULL default '1',
  `attached_item_id` int(11) NOT NULL,
  `attached_kinah_count` bigint(20) NOT NULL,
  `express` tinyint(4) NOT NULL default '0', 
  `recieved_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY (`mail_unique_id`),
  INDEX (`mail_recipient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_effects`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_effects` (
  `player_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_lvl` tinyint NOT NULL,
  `current_time` int(11) NOT NULL,
  `end_time` bigint(13) NOT NULL,
  PRIMARY KEY (`player_id`, `skill_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `item_cooldowns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `item_cooldowns` (
  `player_id` int(11) NOT NULL,
  `delay_id` int(11) NOT NULL,
  `use_delay` SMALLINT UNSIGNED NOT NULL,
  `reuse_time` BIGINT(13) NOT NULL,
  PRIMARY KEY (`player_id`, `delay_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `broker`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `broker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_pointer` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `item_creator` varchar(50),
  `seller` varchar(50) NOT NULL,
  `price` bigint(20) NOT NULL DEFAULT '0',
  `broker_race` enum('ELYOS','ASMODIAN') NOT NULL,
  `expire_time` timestamp NOT NULL DEFAULT '2010-01-01 02:00:00',
  `settle_time` timestamp NOT NULL DEFAULT '2010-01-01 02:00:00',
  `seller_id` int(11) NOT NULL,
  `is_sold` tinyint(1) NOT NULL,
  `is_settled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `bookmark`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bookmark` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `char_id` int(11) NOT NULL,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `world_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `announcements`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `announcements` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `announce` text NOT NULL,
  `faction` enum('ALL','ASMODIANS','ELYOS') NOT NULL DEFAULT 'ALL',
  `type` enum('SHOUT','ORANGE','YELLOW','WHITE','SYSTEM') NOT NULL default 'SYSTEM',
  `delay` int(4) NOT NULL DEFAULT '1800',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_life_stats`
-- ----------------------------

CREATE  TABLE IF NOT EXISTS `player_life_stats` (
  `player_id` INT(11) NOT NULL ,
  `hp` INT(11) NOT NULL DEFAULT 1 ,
  `mp` INT(11) NOT NULL DEFAULT 1 ,
  `fp` INT(11) NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`player_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `siege_locations`
-- ----------------------------

CREATE TABLE IF NOT EXISTS `siege_locations` (
  `id` int(11) NOT NULL,
  `race` enum('ELYOS', 'ASMODIANS', 'BALAUR') NOT NULL,
  `legion_id` int (11) NOT NULL,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `petitions`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `petitions` (
  `id` bigint(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `add_data` varchar(255) default NULL,
  `time` bigint(11) NOT NULL default '0',
  `status` enum('PENDING','IN_PROGRESS','REPLIED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `tasks`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tasks` (
  `id` int(5) NOT NULL,
  `task` varchar(50) NOT NULL,
  `type` enum('FIXED_IN_TIME') NOT NULL,
  `last_activation` timestamp NOT NULL DEFAULT '2010-01-01 00:00:00',
  `start_time` varchar(8) NOT NULL,
  `delay` int(10) NOT NULL,
  `param` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_pets`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_pets` (
  `player_id` int(11) NOT NULL,
  `pet_id` int(11) NOT NULL,
  `decoration` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `hungry_level` int(10) NOT NULL DEFAULT '0',
  `reuse_time` bigint(20) NOT NULL DEFAULT '0',
  `birthday` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `mood_started` bigint(20) NOT NULL DEFAULT '0',
  `counter` int(11) NOT NULL DEFAULT '0',
  `mood_cd_started` bigint(20) NOT NULL DEFAULT '0',
  `gift_cd_started` bigint(20) NOT NULL DEFAULT '0',
  `despawn_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`player_id`, `pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `portal_cooldowns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `portal_cooldowns` (
  `player_id` int(11) NOT NULL,
  `world_id` int(11) NOT NULL,
  `reuse_time` BIGINT(13) NOT NULL,
  PRIMARY KEY (`player_id`, `world_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_passkey`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_passkey` (
  `account_id` int(11) NOT NULL,
  `passkey` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`account_id`,`passkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `guides`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `guides` (
  `guide_id` int(11) NOT NULL auto_increment,
  `player_id` int(11) NOT NULL,
  `title` varchar(80) NOT NULL,
  PRIMARY KEY (`guide_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `ingameshop`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ingameshop` (
  `object_id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `item_count` int(11) NOT NULL DEFAULT '0',
  `item_price` int(11) NOT NULL DEFAULT '0',
  `category` int(11) NOT NULL DEFAULT '0',
  `list` int(11) NOT NULL DEFAULT '0',
  `sales_ranking` int(11) NOT NULL DEFAULT '0',
  `description` varchar(20) NOT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `old_names`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `old_names` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int(11) unsigned NOT NULL,
  `old_name` varchar(50) NOT NULL,
  `new_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_emotions`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_emotions` (
  `player_id` int(11) NOT NULL,
  `emotion` int(11) NOT NULL,
  `remaining` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`, `emotion`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `spawns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `spawns` (
  `spawn_id` int(10) NOT NULL auto_increment,
  `npc_id` int(10) NOT NULL,
  `npc_name` varchar(50) NOT NULL default '',
  `map_id` int(10) NOT NULL,
  `pool_size` int(5) NOT NULL default 1,
  `anchor` varchar(100),
  `handler` enum('RIFT','STATIC') default NULL,
  `spawn_time` enum('ALL','DAY','NIGHT') NOT NULL default 'ALL',
  `walker_id` varchar(100) DEFAULT NULL,
  `random_walk` int(10) NOT NULL default '0',
  `static_id` int(10) NOT NULL default '0',
  `fly` tinyint(1) NOT NULL default '0',
  `respawn_time` int(10) NOT NULL default '0',
  `last_despawn_time` timestamp NULL default NULL,
  `date_added` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `author` varchar(50) NOT NULL default 'system',
  PRIMARY KEY (`spawn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1000000;

-- ----------------------------
-- Table structure for `spawns_loc`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `spawns_loc` (
  `spawn_id` int(10) NOT NULL,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `heading` tinyint(3) NOT NULL,
  KEY `fk_sp_id` (`spawn_id`),
  CONSTRAINT `fk_sp_id` FOREIGN KEY (`spawn_id`) REFERENCES `spawns` (`spawn_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `siege_spawns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `siege_spawns` (
  `spawn_id` int(10) NOT NULL,
  `siege_id` int(10) NOT NULL,
  `race` enum('ELYOS','ASMODIANS','BALAUR') NOT NULL,
  `protector` int(10) default '0',
  `stype` enum('PEACE','GUARD','ARTIFACT','PROTECTOR','MINE','PORTAL','GENERATOR','SPRING','RACEPROTECTOR','UNDERPASS') default NULL,
   PRIMARY KEY (`spawn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `spawn_pools`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `spawn_pools` (
  `pool_id` int(5) NOT NULL auto_increment,
  `pool` tinyint(3) NOT NULL,
   PRIMARY KEY (`pool_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_bind_point`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_bind_point` (
  `player_id` int(11) NOT NULL,
  `map_id` int(11) NOT NULL,
  `x` FLOAT NOT NULL,
  `y` FLOAT NOT NULL,
  `z` FLOAT NOT NULL,
  `heading` int(3) NOT NULL,
  PRIMARY KEY (`player_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_cooldowns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_cooldowns` (
  `player_id` int(11) NOT NULL,
  `cooldown_id` int(6) NOT NULL,
  `reuse_delay` bigint(13) NOT NULL,
  PRIMARY KEY (`player_id`,`cooldown_id`),
  FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `craft_cooldowns`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `craft_cooldowns` (
  `player_id` int(11) unsigned NOT NULL,
  `delay_id` int(11) unsigned NOT NULL,
  `reuse_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`player_id`,`delay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `surveys`
-- ----------------------------
DROP TABLE IF EXISTS `surveys`;
CREATE TABLE `surveys` (
  `unique_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` decimal(20,0) NOT NULL DEFAULT '1',
  `html_text` text NOT NULL,
  `html_radio` varchar(100) NOT NULL DEFAULT 'accept',
  `used` tinyint(1) NOT NULL DEFAULT '0',
  `used_time` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`unique_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `surveys_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `player_motions`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `player_motions` (
  `player_id` int(11) NOT NULL,
  `motion_id` int(3) NOT NULL,
  `time` int(11) NOT NULL default '0',
  `active` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  USING BTREE (`player_id`,`motion_id`),
  CONSTRAINT `motions_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `web_reward`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `web_reward` (
  `unique` int(11) NOT NULL AUTO_INCREMENT,
  `item_owner` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` decimal(20,0) NOT NULL DEFAULT '1',
  `rewarded` tinyint(1) NOT NULL DEFAULT '0',
  `added` varchar(70) DEFAULT '',
  `received` varchar(70) DEFAULT '',
  PRIMARY KEY (`unique`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `weddings`
-- ----------------------------
DROP TABLE IF EXISTS `weddings`;
CREATE TABLE `weddings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `player1` int(11) NOT NULL,
  `player2` int(11) NOT NULL,
  PRIMARY KEY USING BTREE (`id`) ,
  KEY `player1` (`player1`),
  KEY `player2` (`player2`),
  CONSTRAINT `weddings_ibfk_2` FOREIGN KEY (`player2`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `weddings_ibfk_1` FOREIGN KEY (`player1`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `player_vars`
-- ----------------------------
DROP TABLE IF EXISTS `player_vars`;
CREATE TABLE `player_vars` (
  `player_id` int(11) NOT NULL,
  `param` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `time` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`player_id`,`param`),
  CONSTRAINT `player_vars_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `skill_motions`
-- ----------------------------
DROP TABLE IF EXISTS `skill_motions`;

CREATE TABLE `skill_motions` (
  `motion_name` varchar(255) NOT NULL DEFAULT '',
  `skill_id` int(11) NOT NULL,
  `attack_speed` int(11) NOT NULL,
  `weapon_type` varchar(255) NOT NULL,
  `off_weapon_type` varchar(255) NOT NULL,
  `time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`motion_name`,`skill_id`,`attack_speed`,`weapon_type`,`off_weapon_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
