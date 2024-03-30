package ru.yandex.javacource.kolomiets.schedule.manager;

public class TaskValidationException extends RuntimeException {
    public TaskValidationException() {

    }

    public TaskValidationException(String message) {
        super(message);
    }
}
