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

@CrossOrigin
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final AdminDao adminDao;
    private final UserDao userDao;
    private final ForumDao forumDao;

    public AdminController(AdminDao adminDao, UserDao userDao, ForumDao forumDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.forumDao = forumDao;
    }

    @Transactional
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody UserRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String user = request.getUser();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(user)) {
            return ApiResponse.parameterMissing();
        }
        Admin admin = adminDao.getByLogin(login);
        if (admin == null || !password.equals(admin.getPassword())) {
            return ApiResponse.authError();
        }
        User entity = userDao.getByLogin(user);
        if (entity == null) {
            return ApiResponse.entryNotFound();
        }
        userDao.delete(entity);
        return ApiResponse.ok(user);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.POST)
    public ResponseEntity createForum(@RequestBody ForumRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String title = request.getTitle();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(title)) {
            return ApiResponse.parameterMissing();
        }
        Admin admin = adminDao.getByLogin(login);
        if (admin == null || !password.equals(admin.getPassword())) {
            return ApiResponse.authError();
        }
        forumDao.create(new Forum(title));
        return ApiResponse.ok(title);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.DELETE)
    public ResponseEntity deleteForum(@RequestBody ForumRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String title = request.getTitle();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(title)) {
            return ApiResponse.parameterMissing();
        }
        Admin admin = adminDao.getByLogin(login);
        if (admin == null || !password.equals(admin.getPassword())) {
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
    public ResponseEntity renameForum(@RequestBody ForumRenameRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String oldTitle = request.getOldTitle();
        String newTitle = request.getNewTitle();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(oldTitle) ||
                StringUtils.isEmpty(newTitle)) {
            return ApiResponse.parameterMissing();
        }
        Admin admin = adminDao.getByLogin(login);
        if (admin == null || !password.equals(admin.getPassword())) {
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

    private static final class UserRequest {
        private String login;
        private String password;
        private String user;

        UserRequest() {
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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    private static final class ForumRequest {
        private String login;
        private String password;
        private String title;

        ForumRequest() {
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private static final class ForumRenameRequest {
        private String login;
        private String password;
        private String oldTitle;
        private String newTitle;

        ForumRenameRequest() {
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
