package ru.bmstu.iu7.dao.forum;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu7.dao.BaseDao;

import java.util.List;

@Repository
public class ForumDaoImpl extends BaseDao<Forum> implements ForumDao {
    public ForumDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Forum entity) {
        jdbcTemplate.update("INSERT INTO forum (title) VALUES (?);", entity.getTitle());
    }

    @Override
    public void update(Forum entity) {
        jdbcTemplate.update("UPDATE forum SET title = ? WHERE id = ?;", entity.getTitle(), entity.getId());
    }

    @Override
    public void delete(Forum entity) {
        jdbcTemplate.update("DELETE FROM forum WHERE id = ?;", entity.getId());
    }

    @Override
    public Forum get(int id) {
        List<Forum> list = jdbcTemplate.query("SELECT * FROM forum WHERE id = ?;", new ForumRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Forum get(String title) {
        List<Forum> list = jdbcTemplate.query("SELECT * FROM forum WHERE title = ?;", new ForumRowMapper(), title);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Forum> list() {
        return jdbcTemplate.query("SELECT * FROM forum ORDER BY id;", new ForumRowMapper());
    }
}
