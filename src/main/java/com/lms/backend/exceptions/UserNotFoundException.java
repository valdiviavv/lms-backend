package com.lms.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(String id) {
        super("There was no category found with the id: " + id);
    }

}
