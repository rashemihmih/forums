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
        Date date1 = new Date(2000);
        Date date2 = new Date(3000);
        Date date3 = new Date(1000);
        Date date4 = new Date(4000);
        Date date5 = new Date(5000);
        Thread thread1 = new Thread(1, "Thread 1", "Message 1", 1, date1, date1);
        Thread thread2 = new Thread(1, "Thread 2", "Message 2", 1, date2, date2);
        Thread thread3 = new Thread(1, "Thread 3", "Message 3", 1, date3, date3);
        Thread thread4 = new Thread(1, "Thread 4", "Message 4", 1, date4, date4);
        Thread thread5 = new Thread(1, "Thread 5", "Message 5", 1, date5, date5);
        databaseService.addThread(1, thread1.getTitle(), thread1.getMessage(), 1);
        databaseService.addThread(1, thread2.getTitle(), thread2.getMessage(), 1);
        databaseService.addThread(1, thread3.getTitle(), thread3.getMessage(), 1);
        databaseService.addThread(1, thread4.getTitle(), thread4.getMessage(), 1);
        databaseService.addThread(1, thread5.getTitle(), thread5.getMessage(), 1);
        List<Thread> threads = threadDao.list(forum, 3, 2);
        assertEquals(2, threads.size());
        assertEquals(thread2.getTitle(), threads.get(0).getTitle());
        assertEquals(thread2.getMessage(), threads.get(0).getMessage());
        assertEquals(thread2.getUserId(), threads.get(0).getUserId());
        assertEquals(thread2.getForumId(), threads.get(0).getForumId());
        assertEquals(thread1.getTitle(), threads.get(1).getTitle());
        assertEquals(thread1.getMessage(), threads.get(1).getMessage());
        assertEquals(thread1.getUserId(), threads.get(1).getUserId());
        assertEquals(thread1.getForumId(), threads.get(1).getForumId());
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
