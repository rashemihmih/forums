package ru.bmstu.iu7.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.iu7.dao.user.User;
import ru.bmstu.iu7.dao.user.UserDao;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class AuthController {
    public static final String HTTP_SESSION_LOGIN_ATTR = "login";
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestBody Request request, HttpSession httpSession) {
        String login = request.getLogin();
        String password = request.getPassword();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ApiResponse.parameterMissing();
        }
        userDao.create(new User(login, passwordEncoder.encode(password)));
        httpSession.setAttribute(HTTP_SESSION_LOGIN_ATTR, login);
        return ApiResponse.ok(login);
    }

    @RequestMapping(path = "/session", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody Request request, HttpSession httpSession) {
        String login = request.getLogin();
        String password = request.getPassword();
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ApiResponse.parameterMissing();
        }
        User user = userDao.getByLogin(login);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ApiResponse.authError();
        }
        httpSession.setAttribute(HTTP_SESSION_LOGIN_ATTR, login);
        return ApiResponse.ok(login);
    }

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionAuth(HttpSession httpSession) {
        Object httpSessionLogin = httpSession.getAttribute(HTTP_SESSION_LOGIN_ATTR);
        if (httpSessionLogin == null) {
            return ApiResponse.authError();
        }
        User user = userDao.getByLogin(httpSessionLogin.toString());
        if (user == null) {
            return ApiResponse.authError();
        }
        return ApiResponse.ok(user.getLogin());
    }

    @RequestMapping(path = "/session", method = RequestMethod.DELETE)
    public ResponseEntity logout(HttpSession httpSession) {
        Object httpSessionLogin = httpSession.getAttribute(HTTP_SESSION_LOGIN_ATTR);
        if (httpSessionLogin == null) {
            return ApiResponse.authError();
        }
        httpSession.removeAttribute(HTTP_SESSION_LOGIN_ATTR);
        return ApiResponse.ok(httpSessionLogin);
    }

    private static final class Request {
        private String login;
        private String password;

        Request() {
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
}
