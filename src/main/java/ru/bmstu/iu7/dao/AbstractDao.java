package ru.bmstu.iu7.dao;

public interface AbstractDao<T extends BaseDaoEntity> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
