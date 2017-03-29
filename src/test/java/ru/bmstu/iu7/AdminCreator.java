package ru.bmstu.iu7;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;

public class AdminCreator {
    public static void createAdmin(AdminDao adminDao, String login, String password, PasswordEncoder passwordEncoder) {
        adminDao.create(new Admin(login, passwordEncoder.encode(password)));
    }
}
