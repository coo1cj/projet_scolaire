-- db_sr03.forums definition

CREATE TABLE `forums` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- db_sr03.users definition

CREATE TABLE `users` (
  `login` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` tinyint(1) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- db_sr03.messages definition

CREATE TABLE `messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(1024) NOT NULL,
  `time` timestamp NOT NULL,
  `forum` int NOT NULL,
  `user` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`,`forum`),
  KEY `messages_fk` (`forum`),
  KEY `messages_fk_1` (`user`),
  CONSTRAINT `messages_fk` FOREIGN KEY (`forum`) REFERENCES `forums` (`id`),
  CONSTRAINT `messages_fk_1` FOREIGN KEY (`user`) REFERENCES `users` (`login`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- db_sr03.subscriptions definition

CREATE TABLE `subscriptions` (
  `user` varchar(100) NOT NULL,
  `forum` int NOT NULL,
  PRIMARY KEY (`user`,`forum`),
  KEY `subscriptions_fk_1` (`forum`),
  CONSTRAINT `subscriptions_fk` FOREIGN KEY (`user`) REFERENCES `users` (`login`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `subscriptions_fk_1` FOREIGN KEY (`forum`) REFERENCES `forums` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- inserts

INSERT INTO db_sr03.users (login,firstname,lastname,gender,pwd,`role`) VALUES 
('adam@yahoo.com','Adam','Yahoo','Male','abcd1234',1)
,('boris@live.fr','Boris','Boris','Male','00000000',1)
,('root@root.com','Admin','Localhost','Female','admin123',2)
,('spammer@void.io','Guest','Spammer','Male','azertyuiop',0)
,('sr03@utc.fr','Student','GI','Female','123456',1)
,('super@gmail.com','Super','Super','Male','123456',0)
,('val@etu.fr','Val','Val','Male','12345678',2)
;

INSERT INTO db_sr03.forums (title,description) VALUES 
('Avions','Tout ce qui vole plus ou moins bien')
,('Trains','Des motrices à la pelle')
,('Spatial','On va discuter de lancements de fusées')
,('Programmation','Forum d''entraide pour les problèmes situés entre la chaise et le clavier')
,('Java','Forum créé suite à une demande')
;

INSERT INTO db_sr03.subscriptions (`user`,forum) VALUES 
('root@root.com',1)
,('spammer@void.io',1)
,('val@etu.fr',1)
,('adam@yahoo.com',7)
,('boris@live.fr',7)
,('spammer@void.io',7)
,('val@etu.fr',7)
,('adam@yahoo.com',12)
,('root@root.com',13)
,('sr03@utc.fr',13)
;
INSERT INTO db_sr03.subscriptions (`user`,forum) VALUES 
('val@etu.fr',13)
,('root@root.com',14)
;

INSERT INTO db_sr03.messages (content,`time`,forum,`user`) VALUES 
('Rafale','2020-06-05 15:22:51.0',1,'val@etu.fr')
,('Mirage III, Mirage IV, Mirage 2000','2020-06-05 15:24:18.0',1,NULL)
,('Super-Étendart','2020-06-05 15:23:09.0',1,'sr03@utc.fr')
,('J''ai vraiment adoré le lancement de Space X pour la NASA, bravo les US, vous êtes peut être enfin bon à qqch !','2020-06-06 10:43:21.0',12,NULL)
,('Le lancement était super','2020-06-06 14:18:33.0',12,'root@root.com')
,('J''aime les locomotives','2020-06-06 17:58:06.0',7,'boris@live.fr')
,('Bonjour ! Voici le forum de programmation','2020-06-07 13:48:02.0',13,'val@etu.fr')
,('Et pas reporté à cause du mauvais temps ce coup-ci','2020-06-07 13:52:27.0',12,'adam@yahoo.com')
,('Salut ! J''ai un problème avec mon devoir de SR03, j''y comprend rien ','2020-06-07 13:52:55.0',13,'adam@yahoo.com')
,('C''est en Java, j''arrive pas à utiliser Hibernate','2020-06-07 13:53:21.0',13,'adam@yahoo.com')
;
INSERT INTO db_sr03.messages (content,`time`,forum,`user`) VALUES 
('Moi aussi !! je suis perdu','2020-06-07 13:55:38.0',13,'sr03@utc.fr')
,('Voilà, mettez ici vos problèmes de Java','2020-06-07 13:56:50.0',14,'root@root.com')
,('Je viens de créer le forum pour le Java','2020-06-07 13:57:18.0',13,'root@root.com')
;