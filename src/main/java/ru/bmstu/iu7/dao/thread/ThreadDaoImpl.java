package ru.bmstu.iu7.dao.thread;

import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu7.dao.BaseDao;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.main.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ThreadDaoImpl extends BaseDao<Thread> implements ThreadDao {
    protected ThreadDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<Thread> list(Forum forum, int offset, int limit) {
        return jdbcTemplate.query("SELECT * FROM thread JOIN " +
                        "(SELECT id FROM thread WHERE forum_id = ? ORDER BY last_update DESC LIMIT ?, ?) t " +
                        "ON thread.id = t.id ORDER BY thread.last_update DESC;",
                new ThreadRowMapper(), forum.getId(), offset, limit);
    }

    @Override
    public void create(Thread entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO thread (forum_id, title, message, " +
                    "user_id, creation_time, last_update) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entity.getForumId());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getMessage());
            ps.setInt(4, entity.getUserId());
            ps.setTimestamp(5, Timestamp.valueOf(DateUtils.format(entity.getCreationTime())));
            ps.setTimestamp(6, Timestamp.valueOf(DateUtils.format(entity.getLastUpdate())));
            return ps;
        }, keyHolder);
        entity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Thread entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Thread entity) {
        jdbcTemplate.update("DELETE FROM thread WHERE id = ?;", entity.getId());
    }

    @Override
    public Thread get(int id) {
        List<Thread> list = jdbcTemplate.query("SELECT * FROM thread WHERE id = ?;", new ThreadRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }
}
