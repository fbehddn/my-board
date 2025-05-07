-- User
INSERT INTO user (id, email, password, username, created_at, updated_at)
VALUES (1, 'test1@example.com', '{noop}password1', 'UserOne', now(), now()),
       (2, 'test2@example.com', '{noop}password2', 'UserTwo', now(), now());

-- Article
INSERT INTO article (id, title, content, author, user_id, like_count, created_at, updated_at)
VALUES (1, 'First Article', 'This is the first article content.', 'UserOne', 1, 0, now(), now()),
       (2, 'Second Article', 'This is the second article content.', 'UserTwo', 2, 0, now(), now()),
       (3, 'Test Article 3', 'Content for article 3', 'UserOne', 1, 0, now(), now()),
       (4, 'Test Article 4', 'Content for article 4', 'UserOne', 1, 0, now(), now()),
       (5, 'Test Article 5', 'Content for article 5', 'UserOne', 1, 0, now(), now()),
       (6, 'Test Article 6', 'Content for article 6', 'UserOne', 1, 0, now(), now()),
       (7, 'Test Article 7', 'Content for article 7', 'UserOne', 1, 0, now(), now()),
       (8, 'Test Article 8', 'Content for article 8', 'UserOne', 1, 0, now(), now()),
       (9, 'Test Article 9', 'Content for article 9', 'UserOne', 1, 0, now(), now()),
       (10, 'Test Article 10', 'Content for article 10', 'UserOne', 1, 0, now(), now()),
       (11, 'Test Article 11', 'Content for article 11', 'UserOne', 1, 0, now(), now()),
       (12, 'Test Article 12', 'Content for article 12', 'UserOne', 1, 0, now(), now());

-- Comment
INSERT
INTO comment (id, content, author, user_id, article_id, created_at, updated_at)
VALUES (1, 'First comment on article 1', 'UserTwo', 2, 1, now(), now()),
       (2, 'Second comment on article 1', 'UserOne', 1, 1, now(), now()),
       (3, 'Thanks for sharing!', 'UserOne', 1, 2, now(), now());