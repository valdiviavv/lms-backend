package com.lms.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SystemRoleNotFoundException extends RuntimeException {

    public SystemRoleNotFoundException(String id) {
        super("There was no system role with id: " +  id);
    }

}
