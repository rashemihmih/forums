package ru.bmstu.iu7.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.post.Post;
import ru.bmstu.iu7.dao.post.PostDao;
import ru.bmstu.iu7.dao.thread.Thread;
import ru.bmstu.iu7.dao.thread.ThreadDao;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;
import ru.bmstu.iu7.main.controller.common.ApiResponse;
import ru.bmstu.iu7.main.controller.common.PostResponse;
import ru.bmstu.iu7.main.controller.common.SessionService;
import ru.bmstu.iu7.main.utils.DateUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/post")
public class PostController {
    private final SessionService sessionService;
    private final PostDao postDao;
    private final ThreadDao threadDao;
    private final UserDao userDao;

    public PostController(SessionService sessionService, PostDao postDao, ThreadDao threadDao, UserDao userDao) {
        this.sessionService = sessionService;
        this.postDao = postDao;
        this.threadDao = threadDao;
        this.userDao = userDao;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createPost(@RequestBody PostCreateRequest request, HttpSession session) {
        String message = request.getMessage();
        if (StringUtils.isEmpty(message)) {
            return ApiResponse.incorrectRequest();
        }
        User user = sessionService.getUser(session);
        if (user == null) {
            return ApiResponse.authError();
        }
        int threadId = request.getThreadId();
        Thread thread = threadDao.get(threadId);
        if (thread == null) {
            return ApiResponse.entryNotFound();
        }
        int parent = request.getParent();
        if (parent != 0 && postDao.get(parent) == null) {
            return ApiResponse.entryNotFound();
        }
        Date date = new Date();
        Post post = new Post(user.getId(), message, threadId, parent, date);
        postDao.create(post);
        PostResponse postResponse = new PostResponse(post.getId(), user.getLogin(), message, threadId, parent,
                DateUtils.format(date));
        return ApiResponse.ok(postResponse);
    }

    @Transactional
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity listPosts(@RequestParam int thread, @RequestParam int offset, @RequestParam int limit,
                                      @RequestParam(required = false) boolean desc,
                                      HttpSession session) {
        if (offset < 0 || limit <= 0) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        Thread threadEntity = threadDao.get(thread);
        if (threadEntity == null) {
            return ApiResponse.entryNotFound();
        }
        List<PostResponse> responses = new ArrayList<>();
        for (Post post : postDao.list(thread, offset, limit, desc)) {
            String user = userDao.get(post.getUserId()).getLogin();
            responses.add(new PostResponse(post.getId(), user, post.getMessage(), thread, post.getParent(),
                    DateUtils.format(post.getCreationTime())));
        }
        return ApiResponse.ok(responses.toArray());
    }

    private static final class PostCreateRequest {
        private String message;
        private int threadId;
        private int parent;

        PostCreateRequest() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getThreadId() {
            return threadId;
        }

        public void setThreadId(int threadId) {
            this.threadId = threadId;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }
    }
}
