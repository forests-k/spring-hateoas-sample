create TABLE IF NOT EXISTS `prototype`.`user_history` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ユーザー履歴ID'
  , `user_id` INT COMMENT 'ユーザーID'
  , `mail` VARCHAR (256) NOT NULL COMMENT 'メールアドレス'
  , `gender` SMALLINT (1) NOT NULL COMMENT '性別'
  , `password` VARCHAR (256) NOT NULL COMMENT 'パスワード'
  , `birthdate` DATE NOT NULL COMMENT '生年月日'
  , `note` VARCHAR (256) COMMENT '変更事由'
  , `create_user_id` INT NULL COMMENT '作成者ID'
  , `create_timestamp` TIMESTAMP NULL COMMENT '作成日時'
  , PRIMARY KEY (`id`)
) ENGINE = Innodb
, DEFAULT character set utf8
, COMMENT = 'ユーザー履歴'
;