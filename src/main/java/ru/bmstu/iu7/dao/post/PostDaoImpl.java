package ru.bmstu.iu7.dao.post;

import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.bmstu.iu7.dao.BaseDao;
import ru.bmstu.iu7.main.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PostDaoImpl extends BaseDao<Post> implements PostDao {
    protected PostDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Post entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO post (user_id, message, thread_id, " +
                    "parent, creation_time) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getMessage());
            ps.setInt(3, entity.getThreadId());
            ps.setInt(4, entity.getParent());
            ps.setTimestamp(5, Timestamp.valueOf(DateUtils.format(entity.getCreationTime())));
            return ps;
        }, keyHolder);
        entity.setId(keyHolder.getKey().intValue());
        jdbcTemplate.update("UPDATE thread SET last_update = ? WHERE id = ?;", entity.getCreationTime(),
                entity.getThreadId());
    }

    @Override
    public void update(Post entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Post entity) {
        jdbcTemplate.update("DELETE FROM post WHERE id = ?", entity.getId());
    }

    @Override
    public Post get(int id) {
        List<Post> list = jdbcTemplate.query("SELECT * FROM post WHERE id = ?;", new PostRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Post> list(int thread, int offset, int limit, boolean desc) {
        String order = desc ? "DESC" : "";
        return jdbcTemplate.query("SELECT * FROM post JOIN " +
                        "(SELECT id FROM post WHERE thread_id = ? ORDER BY creation_time " + order +" LIMIT ?, ?) p " +
                        "ON post.id = p.id ORDER BY post.creation_time " + order + ';',
                new PostRowMapper(), thread, offset, limit);
    }
}
