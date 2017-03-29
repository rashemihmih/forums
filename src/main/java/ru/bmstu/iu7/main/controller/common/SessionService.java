package ru.bmstu.iu7.main.controller.common;

import org.springframework.stereotype.Service;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {
    private static final String HTTP_SESSION_LOGIN_ATTR = "login";
    private static final String HTTP_SESSION_ADMIN_ATTR = "admin";
    private final UserDao userDao;
    private final AdminDao adminDao;

    public SessionService(UserDao userDao, AdminDao adminDao) {
        this.userDao = userDao;
        this.adminDao = adminDao;
    }

    public User getUser(HttpSession session) {
        Object login = session.getAttribute(HTTP_SESSION_LOGIN_ATTR);
        if (login == null) {
            return null;
        }
        return userDao.get(login.toString());
    }

    public boolean isUserAuthorized(HttpSession session) {
        return getUser(session) != null;
    }

    public void bindUser(HttpSession session, String userLogin) {
        session.setAttribute(HTTP_SESSION_LOGIN_ATTR, userLogin);
    }

    public void unbindUser(HttpSession session) {
        session.removeAttribute(HTTP_SESSION_LOGIN_ATTR);
    }

    public Admin getAdmin(HttpSession session) {
        Object login = session.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (login == null) {
            return null;
        }
        return adminDao.get(login.toString());
    }

    public boolean isAdminAuthorized(HttpSession session) {
        return getAdmin(session) != null;
    }

    public void bindAdmin(HttpSession session, String adminLogin) {
        session.setAttribute(HTTP_SESSION_ADMIN_ATTR, adminLogin);
    }

    public void unbindAdmin(HttpSession session) {
        session.removeAttribute(HTTP_SESSION_ADMIN_ATTR);
    }
}
