package ru.bmstu.iu7.main;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.forum.Forum;
import ru.bmstu.iu7.dao.forum.ForumDao;
import ru.bmstu.iu7.dao.thread.Thread;
import ru.bmstu.iu7.dao.thread.ThreadDao;
import ru.bmstu.iu7.dao.user.User;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/thread")
public class ThreadController {
    private final SessionService sessionService;
    private final ThreadDao threadDao;
    private final ForumDao forumDao;

    public ThreadController(SessionService sessionService, ThreadDao threadDao, ForumDao forumDao) {
        this.sessionService = sessionService;
        this.threadDao = threadDao;
        this.forumDao = forumDao;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createThread(@RequestBody ThreadCreateRequest request, HttpSession session) {
        String forum = request.getForum();
        String title = request.getTitle();
        String message = request.getMessage();
        String creationTime = request.getCreationTime();
        if (StringUtils.isEmpty(forum) || StringUtils.isEmpty(title) || StringUtils.isEmpty(message) ||
                StringUtils.isEmpty(creationTime)) {
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
        Date date = DateUtils.parseDate(creationTime);
        if (date == null) {
            return ApiResponse.incorrectRequest();
        }
        Thread thread = new Thread(forumEntity.getId(), title, message, user.getId(), date);
        threadDao.create(thread);
        return ApiResponse.ok(thread);
    }

    @Transactional
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseEntity listThreads(@RequestParam String forum, HttpSession session) {
        if (StringUtils.isEmpty(forum)) {
            return ApiResponse.incorrectRequest();
        }
        if (!sessionService.isUserAuthorized(session)) {
            return ApiResponse.authError();
        }
        Forum forumEntity = forumDao.get(forum);
        if (forumEntity == null) {
            return ApiResponse.entryNotFound();
        }
        List<Thread> list = threadDao.list(forumEntity);
        return ApiResponse.ok(list.toArray());
    }

    private static final class ThreadCreateRequest {
        private String forum;
        private String title;
        private String message;
        private String creationTime;

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

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }
    }
}
