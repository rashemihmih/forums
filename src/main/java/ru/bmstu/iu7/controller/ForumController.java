package ru.bmstu.iu7.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.controller.common.ApiResponse;
import ru.bmstu.iu7.controller.common.SessionService;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;

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
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getForum(@RequestParam String title, HttpSession session) {
        title = StringUtils.trimWhitespace(title);
        if (StringUtils.isEmpty(title)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        Forum forum = forumDao.get(title);
        if (forum == null) {
            return ApiResponse.entryNotFound();
        }
        return ApiResponse.ok(forum);
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
