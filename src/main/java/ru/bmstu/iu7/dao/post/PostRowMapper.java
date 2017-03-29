package ru.bmstu.iu7.dao.post;

import ru.bmstu.iu7.dao.DaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PostRowMapper implements DaoEntityRowMapper<Post> {
    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        int userId =resultSet.getInt("user_id");
        String message = resultSet.getString("message");
        int threadId = resultSet.getInt("thread_id");
        int parent = resultSet.getInt("parent");
        Date creationTime = resultSet.getTimestamp("creation_time");
        return new Post(id, userId, message, threadId, parent, creationTime);
    }
}
