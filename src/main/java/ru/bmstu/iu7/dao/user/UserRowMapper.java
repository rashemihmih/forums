package ru.bmstu.iu7.dao.user;

import ru.bmstu.iu7.dao.BaseDaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements BaseDaoEntityRowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        final String login = resultSet.getString("login");
        final String password = resultSet.getString("passwd");
        final String about = resultSet.getString("about");
        return new User(login, password, about);
    }
}
