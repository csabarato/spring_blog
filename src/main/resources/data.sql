

INSERT  INTO spring_blog.role (name) VALUES ('ROLE_USER');
INSERT  INTO spring_blog.role (name) VALUES ('ROLE_ADMIN');
INSERT  INTO spring_blog.role (name) VALUES ('ROLE_GUEST');

INSERT  INTO spring_blog.category (name) VALUES ('Sport');
INSERT  INTO spring_blog.category (name) VALUES ('Lifestyle');
INSERT  INTO spring_blog.category (name) VALUES ('Gastronomy');
INSERT  INTO spring_blog.category (name) VALUES ('Music');
INSERT  INTO spring_blog.category (name) VALUES ('Gaming');


-- Insert Users with Roles
INSERT INTO spring_blog.blog_user (email, password, username, enabled, birthdate)

    SELECT 'test@admin.com', '$2y$12$FLqrvKbHurHrSVotL/QFvO1Jd.WsdImcFuEWNZ1OJpZhn3yI3I13u', 'admin', TRUE, sysdate()
    WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_user WHERE username = 'admin');

 INSERT INTO spring_blog.blog_user_roles
    SELECT (SELECT id FROM blog_user WHERE username = 'admin') , (SELECT  id FROM role WHERE name = 'ROLE_ADMIN')
    WHERE NOT EXISTS(SELECT 1 FROM  spring_blog.blog_user_roles
        WHERE user_id = (SELECT id FROM blog_user WHERE username = 'admin') AND
              role_id =  (SELECT  id FROM role WHERE name = 'ROLE_ADMIN'));

INSERT INTO spring_blog.blog_user (email, password, username, enabled, birthdate)

SELECT 'test@user.com', '$2y$12$FLqrvKbHurHrSVotL/QFvO1Jd.WsdImcFuEWNZ1OJpZhn3yI3I13u', 'user', TRUE, sysdate()
WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_user WHERE username = 'user');

INSERT INTO spring_blog.blog_user_roles
SELECT (SELECT id FROM blog_user WHERE username = 'user') , (SELECT  id FROM role WHERE name = 'ROLE_USER')
WHERE NOT EXISTS(SELECT 1 FROM  spring_blog.blog_user_roles
                 WHERE user_id = (SELECT id FROM blog_user WHERE username = 'user') AND
                         role_id =  (SELECT  id FROM role WHERE name = 'ROLE_USER'));


-- Insert Articles
INSERT INTO spring_blog.blog_article (id, created_at, created_by, last_modified_at, last_modified_by, text, title, author_id)
 select null , '2020-12-09 18:51:02.704000000', 'admin', '2020-12-09 18:51:49.203000000', 'admin', 'Sajt torta recept:
- tojás
- sajt
-cukor', 'Sajt', (SELECT id FROM spring_blog.blog_user WHERE username = 'admin')

WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_article WHERE title = 'Sajt');

INSERT INTO spring_blog.blog_article (id, created_at, created_by, last_modified_at, last_modified_by, text, title, author_id)
select null , (SELECT sysdate()), 'admin', (SELECT sysdate()), 'admin', 'Rakott krumpli recept:
- krumpli
- kolbász
- tejföl', 'Rakott krumpli', (SELECT id FROM spring_blog.blog_user WHERE username = 'admin')

WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_article WHERE title = 'Rakott krumpli');

INSERT INTO spring_blog.blog_article (id, created_at, created_by, last_modified_at, last_modified_by, text, title, author_id)
select null , (SELECT sysdate()), 'user', (SELECT sysdate()), 'user', 'Töltött káposzta:
- káposzta
- darált hús
- tejföl', 'Töltött káposzta User módra', (SELECT id FROM spring_blog.blog_user WHERE username = 'user')

WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_article WHERE title = 'Töltött káposzta User módra');

-- Add categories to Articles

    INSERT  INTO spring_blog.blog_article_categories
        SELECT (SELECT id FROM blog_article WHERE title = 'Rakott krumpli'), 'Gastronomy';

    INSERT  INTO spring_blog.blog_article_categories
        SELECT (SELECT id FROM blog_article WHERE title = 'Töltött káposzta User módra'), 'Gastronomy';


    INSERT  INTO spring_blog.blog_article_categories
        SELECT (SELECT id FROM blog_article WHERE title = 'Töltött káposzta User módra'), 'Lifestyle';


    INSERT  INTO spring_blog.blog_article_categories
        SELECT (SELECT id FROM blog_article WHERE title = 'Sajt'), 'Gastronomy';

-- Add Comments

    INSERT  INTO  spring_blog.comment(created_at, created_by, last_modified_at, last_modified_by, text, blog_article_id, blog_user_id)
        SELECT sysdate(), 'user', sysdate(), 'user', 'Porcukor is kell hozzá',
               (SELECT id FROM blog_article WHERE title = 'sajt' LIMIT 1),
               (SELECT id FROM blog_user WHERE username = 'user')
        WHERE NOT EXISTS(SELECT 1 FROM comment
            WHERE text = 'Porcukor is kell hozzá' LIMIT 1);

     INSERT  INTO  spring_blog.comment(created_at, created_by, last_modified_at, last_modified_by, text, blog_article_id, blog_user_id)
        SELECT sysdate(), 'admin', sysdate(), 'admin', 'Ok, köszönöm!',
               (SELECT id FROM blog_article WHERE title = 'sajt' LIMIT 1),
               (SELECT id FROM blog_user WHERE username = 'admin')
        WHERE NOT EXISTS(SELECT 1 FROM comment
            WHERE text = 'Ok, köszönöm!');

-- Add comment likes

    INSERT  INTO comment_user_likes
        VALUES (
            (SELECT id FROM comment WHERE text = 'Porcukor is kell hozzá' LIMIT 1) ,
            (SELECT id FROM blog_user WHERE username = 'admin'));

    INSERT  INTO comment_user_likes
        VALUES (
           (SELECT id FROM comment WHERE text = 'Ok, köszönöm!' LIMIT 1) ,
           (SELECT id FROM blog_user WHERE username = 'user'));

-- Add article likes

    INSERT  INTO article_user_likes
        VALUES (
           (SELECT id FROM blog_article WHERE title = 'Sajt' LIMIT 1) ,
           (SELECT id FROM blog_user WHERE username = 'user'));

    INSERT  INTO article_user_likes
        VALUES (
           (SELECT id FROM blog_article WHERE title = 'Töltött káposzta User módra' LIMIT 1) ,
           (SELECT id FROM blog_user WHERE username = 'admin'));