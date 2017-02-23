package ru.bmstu.iu7.dao.exception;

import ru.bmstu.iu7.dao.exception.DaoException;

public class UserAlreadyExistsException extends DaoException {
    private static final String MESSAGE = "User already exists";
    
    public UserAlreadyExistsException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
