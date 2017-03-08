package ru.bmstu.iu7.dao.user;

import ru.bmstu.iu7.dao.Dao;

public interface UserDao extends Dao<User> {
    User getByLogin(String login);
}
