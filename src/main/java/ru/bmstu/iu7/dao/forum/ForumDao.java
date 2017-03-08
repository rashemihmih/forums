package ru.bmstu.iu7.dao.forum;

import ru.bmstu.iu7.dao.Dao;

import java.util.List;

public interface ForumDao extends Dao<Forum> {
    Forum getByTitle(String title);
    List<Forum> list();
}
