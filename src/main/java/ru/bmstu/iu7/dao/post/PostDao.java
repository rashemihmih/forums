package ru.bmstu.iu7.dao.post;

import ru.bmstu.iu7.dao.Dao;

import java.util.List;

public interface PostDao extends Dao<Post> {
    List<Post> list(int thread, int offset, int limit, boolean desc);
}
