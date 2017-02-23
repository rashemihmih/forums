package ru.bmstu.iu7.dao.user;

import ru.bmstu.iu7.dao.AbstractDao;

public interface UserDao extends AbstractDao<User> {
    User getByLogin(String login);
}
