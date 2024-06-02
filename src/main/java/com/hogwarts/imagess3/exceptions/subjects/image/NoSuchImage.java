package com.hogwarts.imagess3.exceptions.subjects.image;

import com.hogwarts.imagess3.exceptions.NoSuchSubject;

public class NoSuchImage extends NoSuchSubject {
    private static final String subjectName = "Image";

    public NoSuchImage(String errorMessage) {
        super(subjectName, errorMessage);
    }

    public NoSuchImage(String errorMessage, Throwable cause) {
        super(subjectName, errorMessage, cause);
    }
}
