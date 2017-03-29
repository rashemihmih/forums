package ru.bmstu.iu7.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.main.controller.common.ApiResponse;
import ru.bmstu.iu7.main.controller.common.SessionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/forum")
public class ForumController {
    private final SessionService sessionService;
    private final ForumDao forumDao;

    public ForumController(SessionService sessionService, ForumDao forumDao) {
        this.sessionService = sessionService;
        this.forumDao = forumDao;
    }

    @Transactional
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity listForums(HttpSession session) {
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        List<Forum> forums = forumDao.list();
        return ApiResponse.ok(forums.toArray());
    }

}
