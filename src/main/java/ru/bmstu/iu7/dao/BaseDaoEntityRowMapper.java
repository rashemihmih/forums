package ru.bmstu.iu7.dao;

import org.springframework.jdbc.core.RowMapper;

public interface BaseDaoEntityRowMapper<T extends BaseDaoEntity> extends RowMapper<T> {
}
