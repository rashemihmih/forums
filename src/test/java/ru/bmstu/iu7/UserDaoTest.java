package ru.bmstu.iu7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import static org.junit.Assert.*;

public class UserDaoTest extends DaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getNonExistingUserById() {
        assertNull(userDao.get(1));
    }

    @Test
    public void getNonExistingUserByLogin() {
        assertNull(userDao.get("Vasya"));
    }

    @Test
    public void getExistingUserById() {
        String login = "Vasya";
        String password = "123";
        databaseService.addUser(login, password);
        User user = userDao.get(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void getExistingUserByName() {
        String login = "Vasya";
        String password = "123";
        databaseService.addUser(login, password);
        User user = userDao.get(login);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void deleteUser() {
        String login = "Vasya";
        String password = "123";
        databaseService.addUser(login, password);
        userDao.delete(new User(1, login, password));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_profile;");
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void createNewUser() {
        String login = "Vasya";
        String password = "123";
        userDao.create(new User(login, password));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_profile;");
        assertTrue(sqlRowSet.next());
        assertEquals(1, sqlRowSet.getInt("id"));
        assertEquals(login, sqlRowSet.getString("login"));
        assertEquals(password, sqlRowSet.getString("passwd"));
    }

    @Test(expected = DuplicateKeyException.class)
    public void createExistingUser() {
        String login = "Vasya";
        String password = "123";
        databaseService.addUser(login, password);
        userDao.create(new User(login, password));
    }
}
