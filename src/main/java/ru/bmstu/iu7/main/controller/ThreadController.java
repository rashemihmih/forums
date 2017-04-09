package ru.bmstu.iu7.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.dao.thread.Thread;
import ru.bmstu.iu7.dao.thread.ThreadDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;
import ru.bmstu.iu7.main.controller.common.ApiResponse;
import ru.bmstu.iu7.main.controller.common.SessionService;
import ru.bmstu.iu7.main.controller.common.ThreadResponse;
import ru.bmstu.iu7.main.utils.DateUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/thread")
public class ThreadController {
    private final SessionService sessionService;
    private final ThreadDao threadDao;
    private final ForumDao forumDao;
    private final UserDao userDao;

    public ThreadController(SessionService sessionService, ThreadDao threadDao, ForumDao forumDao, UserDao userDao) {
        this.sessionService = sessionService;
        this.threadDao = threadDao;
        this.forumDao = forumDao;
        this.userDao = userDao;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getThread(@RequestParam int id, HttpSession session) {
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        Thread thread = threadDao.get(id);
        if (thread == null) {
            return ApiResponse.entryNotFound();
        }
        String user = userDao.get(thread.getUserId()).getLogin();
        String forum = forumDao.get(thread.getForumId()).getTitle();
        ThreadResponse response = new ThreadResponse(id, forum, thread.getTitle(), thread.getMessage(), user,
                DateUtils.format(thread.getCreationTime()), DateUtils.format(thread.getLastUpdate()));
        return ApiResponse.ok(response);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createThread(@RequestBody ThreadCreateRequest request, HttpSession session) {
        String forum = StringUtils.trimWhitespace(request.getForum());
        String title = StringUtils.trimWhitespace(request.getTitle());
        String message = StringUtils.trimWhitespace(request.getMessage());
        if (StringUtils.isEmpty(forum) || StringUtils.isEmpty(title) || StringUtils.isEmpty(message)) {
            return ApiResponse.incorrectRequest();
        }
        User user = sessionService.getUser(session);
        if (user == null) {
            return ApiResponse.authError();
        }
        Forum forumEntity = forumDao.get(forum);
        if (forumEntity == null) {
            return ApiResponse.entryNotFound();
        }
        Date date = new Date();
        Thread thread = new Thread(forumEntity.getId(), title, message, user.getId(), date, date);
        threadDao.create(thread);
        ThreadResponse response = new ThreadResponse(thread.getId(), forum, title, message, user.getLogin(),
                DateUtils.format(date), DateUtils.format(date));
        return ApiResponse.ok(response);
    }

    @Transactional
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity listThreads(@RequestParam String forum, @RequestParam int offset, @RequestParam int limit,
                                      HttpSession session) {
        forum = StringUtils.trimWhitespace(forum);
        if (StringUtils.isEmpty(forum) || offset < 0 || limit <= 0) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        Forum forumEntity = forumDao.get(forum);
        if (forumEntity == null) {
            return ApiResponse.entryNotFound();
        }
        List<ThreadResponse> responses = new ArrayList<>();
        for (Thread thread : threadDao.list(forumEntity, offset, limit)) {
            String user = userDao.get(thread.getUserId()).getLogin();
            responses.add(new ThreadResponse(thread.getId(), forum, thread.getTitle(), thread.getMessage(), user,
                    DateUtils.format(thread.getCreationTime()), DateUtils.format(thread.getLastUpdate())));
        }
        return ApiResponse.ok(responses.toArray());
    }

    private static final class ThreadCreateRequest {
        private String forum;
        private String title;
        private String message;

        ThreadCreateRequest() {
        }

        public String getForum() {
            return forum;
        }

        public void setForum(String forum) {
            this.forum = forum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
