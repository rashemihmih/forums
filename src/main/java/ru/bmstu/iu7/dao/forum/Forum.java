package ru.bmstu.iu7.dao.forum;

import ru.bmstu.iu7.dao.DaoEntity;

public class Forum implements DaoEntity {
    int id;
    private String title;

    public Forum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Forum(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
