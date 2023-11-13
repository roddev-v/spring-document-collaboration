package com.roddevv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedDocument extends RuntimeException {
    public UnauthorizedDocument() {
        super("You don't have access to this document.");
    }
}

