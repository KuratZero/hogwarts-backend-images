package com.hogwarts.imagess3.controllers.handler;

import com.hogwarts.imagess3.exceptions.ImageUploadException;
import com.hogwarts.imagess3.exceptions.NoSuchImage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(NoSuchImage.class)
    public ResponseEntity<String> onNoSuchResourceException(NoSuchImage e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<String> onNoSuchResourceException(ImageUploadException e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
