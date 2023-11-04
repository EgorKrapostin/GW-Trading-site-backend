package ru.skypro.homework.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * A class for handling an exception for a declaration
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AdNotFoundException extends RuntimeException{
    public AdNotFoundException() {
        super("Ad is not found");
    }
}
