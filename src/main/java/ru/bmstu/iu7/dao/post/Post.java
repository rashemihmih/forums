package ru.bmstu.iu7.dao.post;

import ru.bmstu.iu7.dao.DaoEntity;

import java.util.Date;

public class Post implements DaoEntity {
    private int id;
    private int userId;
    private String message;
    private int threadId;
    private int parent;
    private Date creationTime;

    public Post(int id, int userId, String message, int threadId, int parent, Date creationTime) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.threadId = threadId;
        this.parent = parent;
        this.creationTime = creationTime;
    }

    public Post(int userId, String message, int threadId, int parent, Date creationTime) {
        this.userId = userId;
        this.message = message;
        this.threadId = threadId;
        this.parent = parent;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
