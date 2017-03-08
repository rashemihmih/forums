package ru.bmstu.iu7.dao;

import org.springframework.jdbc.core.RowMapper;

public interface DaoEntityRowMapper<T extends DaoEntity> extends RowMapper<T> {
}
