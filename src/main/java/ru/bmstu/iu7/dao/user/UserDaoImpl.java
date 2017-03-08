package ru.bmstu.iu7.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu7.dao.BaseDao;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(User entity) {
        jdbcTemplate.update("INSERT INTO user_profile (login, passwd) VALUES (?, ?);",
                entity.getLogin(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User entity) {
        jdbcTemplate.update("DELETE FROM user_profile WHERE id = ?;", entity.getId());
    }

    @Override
    public User getByLogin(String login) {
        List<User> list = jdbcTemplate.query("SELECT * FROM user_profile WHERE login = ?;",
                new UserRowMapper(), login);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
