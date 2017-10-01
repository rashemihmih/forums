package ru.bmstu.iu7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.thread.Thread;
import ru.bmstu.iu7.dao.thread.ThreadDao;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ThreadDaoTest extends DaoTest {
    @Autowired
    private ThreadDao threadDao;

    @Test
    public void createThread() {
        databaseService.addUser("Vasya", "123");
        databaseService.addForum("Forum");
        Date date = new Date();
        String title = "Hello";
        String message = "Hi";
        Thread thread = new Thread(1, title, message, 1, date, date);
        threadDao.create(thread);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM thread;");
        assertTrue(sqlRowSet.next());
        assertEquals(1, sqlRowSet.getInt("id"));
        assertEquals(1, sqlRowSet.getInt("forum_id"));
        assertEquals(title, sqlRowSet.getString("title"));
        assertEquals(message, sqlRowSet.getString("message"));
        assertEquals(1, sqlRowSet.getInt("user_id"));
        assertEquals(1, thread.getId());
    }

    @Test
    public void listThreads() {
        databaseService.addUser("Vasya", "123");
        Forum forum = new Forum(1, "Forum");
        databaseService.addForum(forum.getTitle());
        Date date1 = new Date(1000);
        Date date2 = new Date(2000);
        Date date3 = new Date(3000);
        Date date4 = new Date(4000);
        String title1 = "Thread 1";
        String title2 = "Thread 2";
        String title3 = "Thread 3";
        String title4 = "Thread 4";
        String message1 = "Message 1";
        String message2 = "Message 2";
        String message3 = "Message 3";
        String message4 = "Message 4";
        databaseService.addThread(1, title1, message1, 1, date1);
        databaseService.addThread(1, title2, message2, 1, date2);
        databaseService.addThread(1, title3, message3, 1, date3);
        databaseService.addThread(1, title4, message4, 1, date4);
        List<Thread> threads = threadDao.list(forum, 1, 2);
        assertEquals(2, threads.size());
        assertEquals(title3, threads.get(0).getTitle());
        assertEquals(message3, threads.get(0).getMessage());
        assertEquals(title2, threads.get(1).getTitle());
        assertEquals(message2, threads.get(1).getMessage());
    }

    @Test
    public void deleteThread() {
        databaseService.addUser("Vasya", "123");
        databaseService.addForum("Forum");
        databaseService.addThread(1, "Hello", "Hi", 1);
        threadDao.delete(new Thread(1, 0, "", "", 0, new Date(), new Date()));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM thread;");
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void getThread() {
        databaseService.addUser("Vasya", "123");
        databaseService.addForum("Forum");
        String title = "Hello";
        String message = "Hi";
        databaseService.addThread(1, title, message, 1);
        Thread thread = threadDao.get(1);
        assertNotNull(thread);
        assertEquals(1, thread.getUserId());
        assertEquals(title, thread.getTitle());
        assertEquals(message, thread.getMessage());
        assertEquals(1, thread.getUserId());
    }

    @Test
    public void getNonExistingThread() {
        assertNull(threadDao.get(1));
    }
}
