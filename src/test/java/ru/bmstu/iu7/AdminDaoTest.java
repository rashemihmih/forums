package ru.bmstu.iu7;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminDaoTest {
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private AdminDao adminDao;

    @Before
    public void clear() {
        databaseService.clear();
    }

    @Test
    public void getNonExistingAdminByLogin() {
        assertNull(adminDao.get("Admin"));
    }

    @Test
    public void getAdmin() {
        String login = "Admin";
        String password = "123";
        databaseService.addAdmin(login, password);
        Admin admin = adminDao.get(login);
        assertNotNull(admin);
        assertEquals(login, admin.getLogin());
        assertEquals(password, admin.getPassword());
    }
}
