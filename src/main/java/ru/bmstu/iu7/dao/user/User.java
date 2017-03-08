package ru.bmstu.iu7.dao.user;

import ru.bmstu.iu7.dao.DaoEntity;

public class User implements DaoEntity {
    private String login;
    private String password;
    private String about;

    public User(String login, String password, String about) {
        this.login = login;
        this.password = password;
        this.about = about;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        about = "";
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
