package ru.bmstu.iu7.dao;

public interface Dao<T extends DaoEntity> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
