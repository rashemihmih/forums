package ru.bmstu.iu7.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDao<T extends BaseDaoEntity> implements AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;

    protected BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
