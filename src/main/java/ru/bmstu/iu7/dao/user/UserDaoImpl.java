package ru.bmstu.iu7.dao.user;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.bmstu.iu7.dao.BaseDao;
import ru.bmstu.iu7.dao.exception.UserAlreadyExistsException;

import java.util.List;

@Component
public class UserDaoImpl extends BaseDao<User> implements UserDao {
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User getByLogin(String login) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM user_profile WHERE login = ?;",
                new UserRowMapper(), login);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void create(User entity) {
        try {
            jdbcTemplate.update("INSERT INTO user_profile (login, passwd, about) VALUES (?, ?, ?);",
                    entity.getLogin(), entity.getPassword(), entity.getAbout());
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(e);
        }
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE user_profile SET about = ? WHERE login = ?;", entity.getAbout(), entity.getLogin());
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException();
    }
}
