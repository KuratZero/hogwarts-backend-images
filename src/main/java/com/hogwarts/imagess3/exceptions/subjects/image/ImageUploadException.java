package com.hogwarts.imagess3.exceptions.subjects.image;

import com.hogwarts.imagess3.exceptions.SubjectUploadException;

public class ImageUploadException extends SubjectUploadException {
    private static final String subjectName = "Image";

    public ImageUploadException(String errorMessage) {
        super(subjectName, errorMessage);
    }

    public ImageUploadException(String errorMessage, Throwable cause) {
        super(subjectName, errorMessage, cause);
    }
}
