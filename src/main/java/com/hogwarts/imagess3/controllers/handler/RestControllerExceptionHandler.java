package com.hogwarts.imagess3.controllers.handler;

import com.hogwarts.imagess3.exceptions.subjects.image.ImageUploadException;
import com.hogwarts.imagess3.exceptions.NoSuchSubject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(NoSuchSubject.class)
    public ResponseEntity<String> onNoSuchResourceException(NoSuchSubject e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<String> onNoSuchResourceException(ImageUploadException e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
