package ru.bmstu.iu7.dao.thread;

import ru.bmstu.iu7.dao.Dao;

import java.util.List;

public interface ThreadDao extends Dao<Thread> {
    List<Thread> list(int forumId, int offset, int limit);
}
