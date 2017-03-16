package ru.bmstu.iu7.dao.thread;

import ru.bmstu.iu7.dao.DaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ThreadRowMapper implements DaoEntityRowMapper<Thread> {

    @Override
    public Thread mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        int forumId = resultSet.getInt("forum_id");
        String title = resultSet.getString("title");
        String message = resultSet.getString("message");
        int userId = resultSet.getInt("user_id");
        Date creationTime = resultSet.getTimestamp("creation_time");
        return new Thread(id, forumId, title, message, userId, creationTime);
    }
}
