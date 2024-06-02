package com.hogwarts.imagess3.exceptions;

import lombok.Getter;

@Getter
public abstract class AbstractSubjectException extends RuntimeException {
    private final String subjectName;
    private final String errorMessage;

    public AbstractSubjectException(String subjectName, String errorMessage, String summaryMessage) {
        super(summaryMessage);
        this.subjectName = subjectName;
        this.errorMessage = errorMessage;
    }

    public AbstractSubjectException(String subjectName, String errorMessage, String summaryMessage, Throwable cause) {
        super(summaryMessage, cause);
        this.subjectName = subjectName;
        this.errorMessage = errorMessage;
    }
}
