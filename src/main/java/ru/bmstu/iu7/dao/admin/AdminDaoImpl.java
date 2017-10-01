package ru.bmstu.iu7.dao.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu7.dao.BaseDao;

import java.util.List;

@Repository
public class AdminDaoImpl extends BaseDao<Admin> implements AdminDao {
    public AdminDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Admin entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Admin entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Admin entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Admin get(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Admin get(String login) {
        List<Admin> list = jdbcTemplate.query("SELECT * FROM admin WHERE login = ?;", new AdminRowMapper(), login);
        return list.isEmpty() ? null : list.get(0);
    }
}
