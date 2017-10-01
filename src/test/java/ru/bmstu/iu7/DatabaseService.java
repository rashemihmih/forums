package ru.bmstu.iu7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.bmstu.iu7.utils.DateUtils;

import java.util.Date;

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

    public void addThread(int forumId, String title, String message, int userId, Date date) {
        jdbcTemplate.update("INSERT INTO thread (forum_id, title, message, user_id, creation_time, last_update) " +
                "VALUES (?, ?, ?, ?, ?, ?);", forumId, title, message, userId, DateUtils.format(date),
                DateUtils.format(date));
    }

    public void addPost(int userId, String message, int threadId, int parent) {
        jdbcTemplate.update("INSERT INTO post (user_id, message, thread_id, parent, creation_time) VALUES " +
                "(?, ?, ?, ?, NOW())", userId, message, threadId, parent);
    }

    public void addPost(int userId, String message, int threadId, int parent, Date date) {
        jdbcTemplate.update("INSERT INTO post (user_id, message, thread_id, parent, creation_time) VALUES " +
                "(?, ?, ?, ?, ?)", userId, message, threadId, parent, DateUtils.format(date));
    }

    public void addAdmin(String login, String password) {
        jdbcTemplate.update("INSERT INTO admin (login, passwd) VALUES (?, ?);", login, password);
    }
}
