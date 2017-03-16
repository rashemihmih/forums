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
        jdbcTemplate.update("INSERT INTO admin (login, passwd) VALUES (?, ?);", entity.getLogin(),
                entity.getPassword());
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
        List<Admin> list = jdbcTemplate.query("SELECT * FROM admin WHERE id = ?;", new AdminRowMapper(), id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Admin get(String login) {
        List<Admin> list = jdbcTemplate.query("SELECT * FROM admin WHERE login = ?;", new AdminRowMapper(), login);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
