package ru.bmstu.iu7.dao.forum;

import ru.bmstu.iu7.dao.DaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForumRowMapper implements DaoEntityRowMapper<Forum> {
    @Override
    public Forum mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        return new Forum(id, title);
    }
}
