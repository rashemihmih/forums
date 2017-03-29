package ru.bmstu.iu7.main.controller.common;

public class PostResponse {
    private int id;
    private String user;
    private String message;
    private int threadId;
    private int parent;
    private String creationTime;

    public PostResponse(int id, String user, String message, int threadId, int parent, String creationTime) {
        this.id = id;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
