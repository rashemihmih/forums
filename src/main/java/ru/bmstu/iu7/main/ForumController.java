package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/forum")
public class ForumController {
    private final UserDao userDao;
    private final ForumDao forumDao;

    public ForumController(UserDao userDao, ForumDao forumDao) {
        this.userDao = userDao;
        this.forumDao = forumDao;
    }

    @Transactional
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity listForums(HttpSession httpSession) {
        Object httpSessionLogin = httpSession.getAttribute(AuthController.HTTP_SESSION_LOGIN_ATTR);
        if (httpSessionLogin == null) {
            return ApiResponse.authError();
        }
        User user = userDao.getByLogin(httpSessionLogin.toString());
        if (user == null) {
            return ApiResponse.authError();
        }
        List<Forum> forums = forumDao.list();
        return ApiResponse.ok(forums.toArray());
    }

}
