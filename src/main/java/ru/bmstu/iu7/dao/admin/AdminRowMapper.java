package ru.bmstu.iu7.dao.admin;

import ru.bmstu.iu7.dao.DaoEntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements DaoEntityRowMapper<Admin> {
    @Override
    public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
        String login = resultSet.getString("login");
        String password = resultSet.getString("passwd");
        return new Admin(login, password);
    }
}
