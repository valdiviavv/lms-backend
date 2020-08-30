package com.lms.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There was no record for the provided ID")
public class CoursesNotFoundException extends RuntimeException {

    public CoursesNotFoundException(String id) {
        super("There  was no course with id: " + id);
    }

}
