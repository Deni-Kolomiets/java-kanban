package ru.yandex.javacource.kolomiets.schedule.manager;

public class ManagerSaveException extends RuntimeException {
    private String message;

    public ManagerSaveException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
