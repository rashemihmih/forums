package ru.bmstu.iu7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clear() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=FALSE;");
        jdbcTemplate.execute("TRUNCATE TABLE user_profile;");
        jdbcTemplate.execute("TRUNCATE TABLE forum;");
        jdbcTemplate.execute("TRUNCATE TABLE thread;");
        jdbcTemplate.execute("TRUNCATE TABLE post;");
        jdbcTemplate.execute("TRUNCATE TABLE admin;");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=TRUE;");
    }

    public void addUser(String login, String password) {
        jdbcTemplate.update("INSERT INTO user_profile (login, passwd) VALUES (?, ?);", login, password);
    }

    public void addForum(String title) {
        jdbcTemplate.update("INSERT INTO forum (title) VALUES (?);", title);
    }

    public void addThread(int forumId, String title, String message, int userId) {
        jdbcTemplate.update("INSERT INTO thread (forum_id, title, message, user_id, creation_time, last_update) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW());", forumId, title, message, userId);
    }

    public void addPost(int userId, String message, int threadId, int parent) {
        jdbcTemplate.update("INSERT INTO post (user_id, message, thread_id, parent, creation_time) VALUES " +
                "(?, ?, ?, ?, NOW())", userId, message, threadId, parent);
    }

    public void addAdmin(String login, String password) {
        jdbcTemplate.update("INSERT INTO admin (login, passwd) VALUES (?, ?);", login, password);
    }

    public void addUsers() {
        addUser("Albert", "123");
        addUser("Boris", "1231241");
        addUser("Cane", "awqdqw");
        addUser("Deborah", "gfgfgfg");
        addUser("Edward", "sgsdg");
        addUser("Frank", "42342");
    }

    public void addForums() {
        addForum("Auto");
        addForum("Music");
        addForum("Films");
        addForum("Video Games");
        addForum("Sports");
    }
}
