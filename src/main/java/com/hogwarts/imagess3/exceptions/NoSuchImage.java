package com.hogwarts.imagess3.exceptions;

public class NoSuchImage extends RuntimeException {
    public NoSuchImage(String message) {
        super(message);
    }

    public NoSuchImage(String message, Throwable cause) {
        super(message, cause);
    }
}
