package ru.bmstu.iu7.dao.user;

import ru.bmstu.iu7.dao.DaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements DaoEntityRowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("passwd");
        return new User(id, login, password);
    }
}
