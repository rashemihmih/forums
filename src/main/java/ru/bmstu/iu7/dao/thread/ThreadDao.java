package ru.bmstu.iu7.dao.thread;

import ru.bmstu.iu7.dao.Dao;
import ru.bmstu.iu7.dao.forum.Forum;

import java.util.List;

public interface ThreadDao extends Dao<Thread> {
    List<Thread> list(Forum forum, int offset, int limit);
}
