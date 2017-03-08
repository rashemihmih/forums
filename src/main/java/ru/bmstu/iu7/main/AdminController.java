package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private static final String HTTP_SESSION_ADMIN_ATTR = "admin";
    private final AdminDao adminDao;
    private final UserDao userDao;
    private final ForumDao forumDao;

    public AdminController(AdminDao adminDao, UserDao userDao, ForumDao forumDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.forumDao = forumDao;
    }

    @RequestMapping(path = "/session", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthRequest request, HttpSession httpSession) {
        String login = request.getLogin();
        String password = request.getPassword();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ApiResponse.parameterMissing();
        }
        Admin admin = adminDao.getByLogin(login);
        if (admin == null || !password.equals(admin.getPassword())) {
            return ApiResponse.authError();
        }
        httpSession.setAttribute(HTTP_SESSION_ADMIN_ATTR, login);
        return ApiResponse.ok(login);
    }

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionAuth(HttpSession httpSession) {
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        Admin admin = adminDao.getByLogin(adminAttr.toString());
        if (admin == null) {
            return ApiResponse.authError();
        }
        return ApiResponse.ok(adminAttr);
    }

    @RequestMapping(path = "/session", method = RequestMethod.DELETE)
    public ResponseEntity logout(HttpSession httpSession) {
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        httpSession.removeAttribute(HTTP_SESSION_ADMIN_ATTR);
        return ApiResponse.ok(adminAttr);
    }

    @Transactional
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody UserRequest request, HttpSession httpSession) {
        String login = request.getLogin();
        if (StringUtils.isEmpty(login)) {
            return ApiResponse.parameterMissing();
        }
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        Admin admin = adminDao.getByLogin(adminAttr.toString());
        if (admin == null) {
            return ApiResponse.authError();
        }
        User user = userDao.getByLogin(login);
        if (user == null) {
            return ApiResponse.entryNotFound();
        }
        userDao.delete(user);
        return ApiResponse.ok(login);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.POST)
    public ResponseEntity createForum(@RequestBody ForumRequest request, HttpSession httpSession) {
        String title = request.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiResponse.parameterMissing();
        }
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        Admin admin = adminDao.getByLogin(adminAttr.toString());
        if (admin == null) {
            return ApiResponse.authError();
        }
        forumDao.create(new Forum(title));
        return ApiResponse.ok(title);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.DELETE)
    public ResponseEntity deleteForum(@RequestBody ForumRequest request, HttpSession httpSession) {
        String title = request.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiResponse.parameterMissing();
        }
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        Admin admin = adminDao.getByLogin(adminAttr.toString());
        if (admin == null) {
            return ApiResponse.authError();
        }
        Forum forum = forumDao.getByTitle(title);
        if (forum == null) {
            return ApiResponse.entryNotFound();
        }
        forumDao.delete(forum);
        return ApiResponse.ok(title);
    }

    @Transactional
    @RequestMapping(path = "/forum/rename", method = RequestMethod.POST)
    public ResponseEntity renameForum(@RequestBody ForumRenameRequest request, HttpSession httpSession) {
        String oldTitle = request.getOldTitle();
        String newTitle = request.getNewTitle();
        if (StringUtils.isEmpty(oldTitle) || StringUtils.isEmpty(newTitle)) {
            return ApiResponse.parameterMissing();
        }
        Object adminAttr = httpSession.getAttribute(HTTP_SESSION_ADMIN_ATTR);
        if (adminAttr == null) {
            return ApiResponse.authError();
        }
        Admin admin = adminDao.getByLogin(adminAttr.toString());
        if (admin == null) {
            return ApiResponse.authError();
        }
        Forum forum = forumDao.getByTitle(oldTitle);
        if (forum == null) {
            return ApiResponse.entryNotFound();
        }
        forum.setTitle(newTitle);
        forumDao.update(forum);
        return ApiResponse.ok(newTitle);
    }

    private static final class AuthRequest {
        private String login;
        private String password;

        AuthRequest() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static final class UserRequest {
        private String login;

        UserRequest() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    private static final class ForumRequest {
        private String title;

        ForumRequest() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private static final class ForumRenameRequest {
        private String oldTitle;
        private String newTitle;

        ForumRenameRequest() {
        }

        public String getOldTitle() {
            return oldTitle;
        }

        public void setOldTitle(String oldTitle) {
            this.oldTitle = oldTitle;
        }

        public String getNewTitle() {
            return newTitle;
        }

        public void setNewTitle(String newTitle) {
            this.newTitle = newTitle;
        }
    }

}