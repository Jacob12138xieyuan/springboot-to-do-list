package com.rakuten.todolist.exception;

public class ValidationException extends Exception{
    private final String field;
    private final String message;

    public ValidationException (String field, String message1) {
        this.field = field;
        this.message = message1;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
