package ru.bmstu.iu7.controller.common;

public class ThreadResponse {
    private int id;
    private String forum;
    private String title;
    private String message;
    private String user;
    private String creationTime;
    private String lastUpdate;

    public ThreadResponse(int id, String forum, String title, String message, String user, String creationTime, String lastUpdate) {
        this.id = id;
        this.forum = forum;
        this.title = title;
        this.message = message;
        this.user = user;
        this.creationTime = creationTime;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
