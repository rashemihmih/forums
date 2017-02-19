CREATE TABLE user_profile (
  id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  username VARCHAR(50)        NOT NULL,
  email    VARCHAR(50)        NOT NULL UNIQUE KEY,
  about    TEXT
)
  DEFAULT CHARSET = utf8;

CREATE TABLE forum (
  id      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name    VARCHAR(50)        NOT NULL UNIQUE KEY,
  user_id INT                NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE
)
  DEFAULT CHARSET = utf8;

CREATE TABLE thread (
  id            INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  forum_id      INT                NOT NULL,
  title         VARCHAR(50)        NOT NULL,
  message       TEXT               NOT NULL,
  user_id       INT                NOT NULL,
  creation_time DATETIME           NOT NULL,
  posts         INT                NOT NULL DEFAULT 0,
  likes         INT                NOT NULL DEFAULT 0,
  FOREIGN KEY (forum_id) REFERENCES forum (id)
    ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  KEY (forum_id, creation_time),
  KEY (user_id, creation_time)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE post (
  id            INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  user_id       INT                NOT NULL,
  message       TEXT               NOT NULL,
  forum_id      INT                NOT NULL,
  thread_id     INT                NOT NULL,
  parent        INT                NULL     DEFAULT NULL,
  creation_time DATETIME           NOT NULL,
  likes         INT                NOT NULL DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  FOREIGN KEY (forum_id) REFERENCES forum (id)
    ON DELETE CASCADE,
  FOREIGN KEY (thread_id) REFERENCES thread (id)
    ON DELETE CASCADE,
  KEY (thread_id, creation_time)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE following (
  follower INT NOT NULL,
  followee INT NOT NULL,
  UNIQUE KEY (follower, followee),
  FOREIGN KEY (follower) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  FOREIGN KEY (followee) REFERENCES user_profile (id)
    ON DELETE CASCADE
)
  DEFAULT CHARSET = utf8;

CREATE TABLE thread_likes (
  user_id   INT NOT NULL,
  thread_id INT NOT NULL,
  UNIQUE KEY (user_id, thread_id),
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  FOREIGN KEY (thread_id) REFERENCES thread (id)
    ON DELETE CASCADE
)
  DEFAULT CHARSET = utf8;

CREATE TABLE post_likes (
  user_id INT NOT NULL,
  post_id INT NOT NULL,
  UNIQUE KEY (user_id, post_id),
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  FOREIGN KEY (post_id) REFERENCES post (id)
    ON DELETE CASCADE
)
  DEFAULT CHARSET = utf8;