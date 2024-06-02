package com.hogwarts.imagess3.exceptions.subjects.file;

import com.hogwarts.imagess3.exceptions.NoSuchSubject;

public class NoSuchFile extends NoSuchSubject {
    private static final String subjectName = "File";

    public NoSuchFile(String errorMessage) {
        super(subjectName, errorMessage);
    }

    public NoSuchFile(String errorMessage, Throwable cause) {
        super(subjectName, errorMessage, cause);
    }
}
