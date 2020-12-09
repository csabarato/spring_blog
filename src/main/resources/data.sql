

INSERT IGNORE INTO spring_blog.role (name) VALUES ('ROLE_USER');
INSERT IGNORE INTO spring_blog.role (name) VALUES ('ROLE_ADMIN');
INSERT IGNORE INTO spring_blog.role (name) VALUES ('ROLE_GUEST');

INSERT IGNORE INTO spring_blog.category (name) VALUES ('Sport');
INSERT IGNORE INTO spring_blog.category (name) VALUES ('Lifestyle');
INSERT IGNORE INTO spring_blog.category (name) VALUES ('Gastronomy');
INSERT IGNORE INTO spring_blog.category (name) VALUES ('Music');
INSERT IGNORE INTO spring_blog.category (name) VALUES ('Gaming');

INSERT INTO spring_blog.blog_user (email, password, username, enabled)

    SELECT 'test@admin.com', '$2y$12$FLqrvKbHurHrSVotL/QFvO1Jd.WsdImcFuEWNZ1OJpZhn3yI3I13u', 'test admin', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM spring_blog.blog_user WHERE username = 'test admin');


INSERT INTO spring_blog.blog_user_roles
    SELECT (SELECT id FROM blog_user WHERE username = 'test admin') , (SELECT  id FROM role WHERE name = 'ROLE_ADMIN')
    WHERE NOT EXISTS(SELECT 1 FROM  spring_blog.blog_user_roles
        WHERE user_id = (SELECT id FROM blog_user WHERE username = 'test admin') AND
              role_id =  (SELECT  id FROM role WHERE name = 'ROLE_ADMIN'));