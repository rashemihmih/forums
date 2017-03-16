package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.admin.Admin;
import ru.bmstu.iu7.dao.admin.AdminDao;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.dao.thread.Thread;
import ru.bmstu.iu7.dao.thread.ThreadDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final AdminDao adminDao;
    private final UserDao userDao;
    private final ForumDao forumDao;
    private final ThreadDao threadDao;

    public AdminController(PasswordEncoder passwordEncoder, SessionService sessionService, AdminDao adminDao,
                           UserDao userDao, ForumDao forumDao, ThreadDao threadDao) {
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.forumDao = forumDao;
        this.threadDao = threadDao;
    }

    @RequestMapping(path = "/session", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthRequest request, HttpSession session) {
        String login = request.getLogin();
        String password = request.getPassword();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ApiResponse.incorrectRequest();
        }
        Admin admin = adminDao.get(login);
        if (admin == null || !passwordEncoder.matches(password, admin.getPassword())) {
            return ApiResponse.authError();
        }
        sessionService.bindAdmin(session, login);
        return ApiResponse.ok(login);
    }

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionAuth(HttpSession session) {
        Admin admin = sessionService.getAdmin(session);
        if (admin == null) {
            return ApiResponse.authError();
        }
        return ApiResponse.ok(admin.getLogin());
    }

    @RequestMapping(path = "/session", method = RequestMethod.DELETE)
    public ResponseEntity logout(HttpSession session) {
        sessionService.unbindAdmin(session);
        return ApiResponse.ok();
    }

    @Transactional
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody UserRequest request, HttpSession session) {
        String login = request.getLogin();
        if (StringUtils.isEmpty(login)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isAdminAuthorized(session)) {
            return ApiResponse.authError();
        }
        User user = userDao.get(login);
        if (user == null) {
            return ApiResponse.entryNotFound();
        }
        userDao.delete(user);
        return ApiResponse.ok(login);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.POST)
    public ResponseEntity createForum(@RequestBody ForumRequest request, HttpSession session) {
        String title = request.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isAdminAuthorized(session)) {
            return ApiResponse.authError();
        }
        forumDao.create(new Forum(title));
        return ApiResponse.ok(title);
    }

    @Transactional
    @RequestMapping(path = "/forum", method = RequestMethod.DELETE)
    public ResponseEntity deleteForum(@RequestBody ForumRequest request, HttpSession session) {
        String title = request.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isAdminAuthorized(session)) {
            return ApiResponse.authError();
        }
        Forum forum = forumDao.get(title);
        if (forum == null) {
            return ApiResponse.entryNotFound();
        }
        forumDao.delete(forum);
        return ApiResponse.ok(title);
    }

    @Transactional
    @RequestMapping(path = "/thread", method = RequestMethod.DELETE)
    public ResponseEntity deleteThread(@RequestBody IdRequest request, HttpSession session) {
        if (!sessionService.isAdminAuthorized(session)) {
            return ApiResponse.authError();
        }
        Thread thread = threadDao.get(request.getId());
        if (thread == null) {
            return ApiResponse.entryNotFound();
        }
        threadDao.delete(thread);
        return ApiResponse.ok(thread);
    }

    @Transactional
    @RequestMapping(path = "/forum/rename", method = RequestMethod.POST)
    public ResponseEntity renameForum(@RequestBody ForumRenameRequest request, HttpSession session) {
        String oldTitle = request.getOldTitle();
        String newTitle = request.getNewTitle();
        if (StringUtils.isEmpty(oldTitle) || StringUtils.isEmpty(newTitle)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isAdminAuthorized(session)) {
            return ApiResponse.authError();
        }
        Forum forum = forumDao.get(oldTitle);
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

    private static final class IdRequest {
        private int id;

        IdRequest() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
