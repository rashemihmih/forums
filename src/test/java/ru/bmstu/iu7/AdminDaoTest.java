package ru.bmstu.iu7;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;

import static org.junit.Assert.*;

public class AdminDaoTest extends DaoTest {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void getNonExistingAdmin() {
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
