package com.hogwarts.imagess3.exceptions;

import java.util.function.BiFunction;

public class NoSuchSubject extends AbstractSubjectException {
    protected static final BiFunction<String, String, String> errorMessageFabric =
            (subjectName, errorMessage) -> String.format("No such %s: %s", subjectName, errorMessage);

    public NoSuchSubject(String subjectName, String errorMessage) {
        super(subjectName, errorMessage, errorMessageFabric.apply(subjectName, errorMessage));
    }

    public NoSuchSubject(String subjectName, String errorMessage, Throwable cause) {
        super(subjectName, errorMessage, errorMessageFabric.apply(subjectName, errorMessage), cause);
    }
}
