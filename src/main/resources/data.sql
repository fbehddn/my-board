DROP PROCEDURE IF EXISTS seed_articles;
DELIMITER $$

INSERT INTO `user` (id, email, password, username, created_at, updated_at)
VALUES (1, 'test@example.com', '{bcrypt}pwd', 'enzo', NOW(), NOW());

CREATE PROCEDURE seed_articles(IN n INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < n DO
            INSERT INTO article (title,content,author,user_id,created_at,updated_at)
            VALUES (
                       CONCAT('테스트 제목 ', i+1),
                       CONCAT('임의 내용 ',  i+1),
                       'enzo',
                       1,
                       NOW(), NOW()
                   );
            SET i = i + 1;
        END WHILE;
END$$
DELIMITER ;

CALL seed_articles(100000);
