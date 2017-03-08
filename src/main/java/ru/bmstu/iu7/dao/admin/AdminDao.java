package ru.bmstu.iu7.dao.admin;

import ru.bmstu.iu7.dao.Dao;

public interface AdminDao extends Dao<Admin> {
    Admin getByLogin(String login);
}
