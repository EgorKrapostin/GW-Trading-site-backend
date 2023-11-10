package ru.skypro.homework.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;
/**
 * A class for handling exceptions related to errors
 */
@RestControllerAdvice
public class TradingExceptionHandler {

    @ExceptionHandler (value = {CommentNotFoundException.class})
    public ResponseEntity<?> handleCommentNotFoundException() {
        String message = "Комментарии отсутствуют";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler (value = {AdNotFoundException.class})
    public ResponseEntity<?> handleAdNotFoundException() {
        String message = "Объявление отсутствует";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<?> handleAuthorizationException() {
        String message = "Необходима авторизация";
        return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException() {
        String message = "Нет прав на это действие";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        String message = "Внутренняя ошибка сервера";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
