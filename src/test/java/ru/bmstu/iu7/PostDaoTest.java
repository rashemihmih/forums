package ru.bmstu.iu7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.bmstu.iu7.dao.post.Post;
import ru.bmstu.iu7.dao.post.PostDao;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PostDaoTest extends DaoTest {
    @Autowired
    private PostDao postDao;

    @Override
    public void init() {
        super.init();
        databaseService.addUser("Vasya", "123");
        databaseService.addForum("Forum");
        databaseService.addThread(1, "Thread", "Message", 1);
    }

    @Test
    public void createPost() {
        Post post = new Post(1, "hello", 1, 0, new Date());
        postDao.create(post);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM post;");
        assertTrue(sqlRowSet.next());
        assertEquals(1, sqlRowSet.getInt("id"));
        assertEquals(1, sqlRowSet.getInt("user_id"));
        assertEquals(post.getMessage(), sqlRowSet.getString("message"));
        assertEquals(1, sqlRowSet.getInt("thread_id"));
        assertEquals(0, sqlRowSet.getInt("parent"));
        assertEquals(1, post.getId());
    }

    @Test
    public void deletePost() {
        databaseService.addPost(1, "hello", 1, 0);
        postDao.delete(new Post(1, 1, "", 1, 0, new Date()));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM post;");
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void getPost() {
        String message = "hello";
        databaseService.addPost(1, message, 1, 0);
        Post post = postDao.get(1);
        assertNotNull(post);
        assertEquals(1, post.getId());
        assertEquals(0, post.getParent());
        assertEquals(1, post.getThreadId());
        assertEquals(1, post.getUserId());
        assertEquals(message, post.getMessage());
    }

    @Test
    public void getNonExistingPost() {
        assertNull(postDao.get(1));
    }

    @Test
    public void listPosts() {
        String message1 = "message 1";
        String message2 = "message 2";
        String message3 = "message 3";
        String message4 = "message 4";
        Date date1 = new Date(1000);
        Date date2 = new Date(2000);
        Date date3 = new Date(3000);
        Date date4 = new Date(4000);
        databaseService.addPost(1, message1, 1, 0, date1);
        databaseService.addPost(1, message2, 1, 0, date2);
        databaseService.addPost(1, message3, 1, 0, date3);
        databaseService.addPost(1, message4, 1, 0, date4);
        List<Post> listDesc = postDao.list(1, 1, 2, true);
        assertEquals(2, listDesc.size());
        assertEquals(message3, listDesc.get(0).getMessage());
        assertEquals(message2, listDesc.get(1).getMessage());
        List<Post> listAsc = postDao.list(1, 1, 2, false);
        assertEquals(2, listDesc.size());
        assertEquals(message2, listAsc.get(0).getMessage());
        assertEquals(message3, listAsc.get(1).getMessage());
    }
}
