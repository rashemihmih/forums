package ru.bmstu.iu7.dao.admin;

import ru.bmstu.iu7.dao.DaoEntity;

public class Admin implements DaoEntity {
    private String login;
    private String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
