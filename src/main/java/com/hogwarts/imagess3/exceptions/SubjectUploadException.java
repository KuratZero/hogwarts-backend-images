package com.hogwarts.imagess3.exceptions;

import java.util.function.BiFunction;

public class SubjectUploadException extends AbstractSubjectException {
    protected static final BiFunction<String, String, String> errorMessageFabric =
            (subjectName, errorMessage) -> String.format("Error upload %s: %s", subjectName, errorMessage);


    public SubjectUploadException(String subjectName, String errorMessage) {
        super(subjectName, errorMessage, errorMessageFabric.apply(subjectName, errorMessage));
    }

    public SubjectUploadException(String subjectName, String errorMessage, Throwable cause) {
        super(subjectName, errorMessage, errorMessageFabric.apply(subjectName, errorMessage), cause);
    }
}
