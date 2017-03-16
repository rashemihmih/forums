CREATE TABLE user_profile (
  id     INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  login  VARCHAR(50)        NOT NULL UNIQUE KEY,
  passwd VARCHAR(80)        NOT NULL
)
  DEFAULT CHARSET = utf8;

CREATE TABLE forum (
  id    INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  title VARCHAR(50)        NOT NULL UNIQUE KEY
)
  DEFAULT CHARSET = utf8;

CREATE TABLE thread (
  id            INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  forum_id      INT                NOT NULL,
  title         VARCHAR(50)        NOT NULL,
  message       TEXT               NOT NULL,
  user_id       INT                NOT NULL,
  creation_time DATETIME           NOT NULL,
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
  FOREIGN KEY (user_id) REFERENCES user_profile (id)
    ON DELETE CASCADE,
  FOREIGN KEY (forum_id) REFERENCES forum (id)
    ON DELETE CASCADE,
  FOREIGN KEY (thread_id) REFERENCES thread (id)
    ON DELETE CASCADE,
  KEY (thread_id, creation_time)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE admin (
  id     INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  login  VARCHAR(50)        NOT NULL UNIQUE KEY,
  passwd VARCHAR(80)       NOT NULL
)
  DEFAULT CHARSET = utf8;
