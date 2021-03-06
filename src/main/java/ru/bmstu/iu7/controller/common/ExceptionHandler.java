package ru.bmstu.iu7.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.debug("Exception: ", e);
        return ApiResponse.incorrectRequest();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.debug("Exception: ", e);
        return ApiResponse.incorrectRequest();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleDataAccessException(DataAccessException e) {
        logger.error("Exception: ", e);
        return ApiResponse.dbError();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleDuplicateKeyException(DuplicateKeyException e) {
        logger.debug("Exception: ", e);
        return ApiResponse.duplicateEntry();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleDuplicateKeyException(MethodArgumentTypeMismatchException e) {
        logger.debug("Exception: ", e);
        return ApiResponse.duplicateEntry();
    }
}
