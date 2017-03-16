package ru.bmstu.iu7.main;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AdminService {
    private PasswordEncoder passwordEncoder;
    private AdminDao adminDao;

    public AdminService(PasswordEncoder passwordEncoder, AdminDao adminDao) {
        this.passwordEncoder = passwordEncoder;
        this.adminDao = adminDao;
    }

    public void createAdmin(String login, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] bytes = digest.digest(password.getBytes());
            String hash = new BigInteger(1, bytes).toString(16);
            adminDao.create(new Admin(login, passwordEncoder.encode(hash)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
