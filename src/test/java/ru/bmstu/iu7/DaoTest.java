package ru.bmstu.iu7;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class DaoTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DatabaseService databaseService;

    @Before
    public void init() {
        databaseService.clear();
    }
}
