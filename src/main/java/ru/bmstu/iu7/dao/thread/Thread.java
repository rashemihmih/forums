package ru.bmstu.iu7.dao.thread;

import ru.bmstu.iu7.dao.DaoEntity;

import java.util.Date;

public class Thread implements DaoEntity {
    private int id;
    private int forumId;
    private String title;
    private String message;
    private int userId;
    private Date creationTime;
    private Date lastUpdate;

    public Thread(int id, int forumId, String title, String message, int userId, Date creationTime, Date lastUpdate) {
        this.id = id;
        this.forumId = forumId;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.creationTime = creationTime;
        this.lastUpdate = lastUpdate;
    }

    public Thread(int forumId, String title, String message, int userId, Date creationTime, Date lastUpdate) {
        this.forumId = forumId;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.creationTime = creationTime;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
