INSERT INTO `question_bank` (question_id,quizz_question,question_difficulty,question_multianswer,category,correct_answer) VALUES (14,'What is the smallest planet in our solar system?',1,4,'Science',0),(15,'What is the name of the worlds largest social media platform?',2,4,'Technology',0),(16,'Which country has the worlds largest population?',3,4,'World affairs',0),(17,'Who holds the record for the most goals scored in a single FIFA World Cup tournament?',3,4,'Sport',0),(18,'Who is the GOAT in football?',3,6,'Sport',0);
INSERT INTO `question_answer` VALUES (28,'Mars', 14),(29,'Venus',14),(30,'Mercury',14),(31,'Pluto',14),(32,'Twitter',15),(33,'Instagram',15),(34,'Facebook',15),(35,'LinkedIn',15),(36,'India',15),(37,'Russia',15),(38,'China',15),(39,'United States',15),(40,'Diego Maradona',17),(41,'Pele',17),(42,'Ronaldo',17),(43,'Just Fontaine',17),(44,'Tien Linh',18),(45,'Lionel Messi', 18),(46,'Cristiano Ronaldo',18),(47,'Neymar Jr',18),(48,'Mbappe',18),(49,'Halland',18);
INSERT INTO `quizz` (quizz_id,host_id, title, description, difficulty, created_at, started_at, ended_at,score, type) VALUES (3,2,'Analysis and Design of Information Systems','A mini test',2,'2022-05-23','2024-06-01','2023-06-01',10,0),(4,1,'Distributed Database Management Systems','Mid-term test',1,'2022-03-31','2024-04-01','2023-04-01',10,0),(5,1,'Introduction to Artificial Intelligence','End test',3,'2022-05-31','2024-06-01','2023-06-01',10,0),(6,1,'Computer Networks','End test',1,'2022-05-31','2024-06-01','2023-06-01',10,0),(7,1,'Analysis and Design of Information Systems','End test',2,'2022-05-31','2024-06-01','2023-06-01',10,0);
INSERT INTO `quizzes_questions` VALUES (7,18);
--INSERT INTO `roles` VALUES (0,'ROLE_ADMIN'),(1,'ROLE_TEACHER'),(2,'ROLE_STUDENT');
--INSERT INTO `users` VALUES (0,'GB', '2003-12-16', 'Nguyen Tuan Tai', 1,'','123','','E21CQCN04-B','B21DCCN007'), (1,'GB', '2003-01-16', 'Nguyen Tuan Thuan', 1,'','123','','E21CQCN04-B','B21DCCN009'), (2,'HD', '2003-02-16', 'Nguyen Ngoc Trinh', 0,'','123','','E21CQCN03-B','B21DCDT007');
--INSERT INTO `user_roles` VALUES (0,0), (0,1), (1,1), (2,2);