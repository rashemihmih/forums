package ru.bmstu.iu7;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bmstu.iu7.dao.admin.AdminDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForumsApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        //AdminCreator.createAdmin(adminDao, "", "", passwordEncoder);
    }

}
