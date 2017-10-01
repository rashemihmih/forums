package ru.bmstu.iu7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;

import java.util.List;

import static org.junit.Assert.*;

public class ForumDaoTest extends DaoTest {
    @Autowired
    private ForumDao forumDao;

    @Test
    public void createForum() {
        String title = "Hello";
        forumDao.create(new Forum(title));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM forum;");
        assertTrue(sqlRowSet.next());
        assertEquals(title, sqlRowSet.getString("title"));
    }

    @Test(expected = DuplicateKeyException.class)
    public void createAlreadyExistingForum() {
        String title = "Hello";
        databaseService.addForum(title);
        forumDao.create(new Forum(title));
    }

    @Test
    public void renameForum() {
        String title = "Hello";
        databaseService.addForum(title);
        String newTitle = "Hi";
        forumDao.update(new Forum(1, newTitle));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM forum;");
        assertTrue(sqlRowSet.next());
        assertEquals(newTitle, sqlRowSet.getString("title"));
    }

    @Test
    public void deleteForum() {
        databaseService.addForum("Hello");
        forumDao.delete(new Forum(1, ""));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM forum;");
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void getForumById() {
        String title = "Hello";
        databaseService.addForum(title);
        Forum forum = forumDao.get(1);
        assertNotNull(forum);
        assertEquals(title, forum.getTitle());
    }

    @Test
    public void getForumByTitle() {
        String title = "Hello";
        databaseService.addForum(title);
        Forum forum = forumDao.get(title);
        assertNotNull(forum);
        assertEquals(title, forum.getTitle());
    }

    @Test
    public void getNonExistingForumById() {
        assertNull(forumDao.get(1));
    }

    @Test
    public void getNonExistingForumByTitle() {
        assertNull(forumDao.get("Hello"));
    }

    @Test
    public void listForums() {
        String title1 = "Forum 1";
        String title2 = "Forum 2";
        String title3 = "Forum 3";
        databaseService.addForum(title1);
        databaseService.addForum(title2);
        databaseService.addForum(title3);
        List<Forum> forums = forumDao.list();
        assertEquals(3, forums.size());
        assertEquals(title1, forums.get(0).getTitle());
        assertEquals(title2, forums.get(1).getTitle());
        assertEquals(title3, forums.get(2).getTitle());
    }
}
