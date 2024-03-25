package ru.yandex.javacource.kolomiets.schedule.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    protected int epicId;
    protected int epic;

    public Subtask(String title, String description, Status status, int epic) {
        super(title, description, status);
        this.epic = epic;
        //this.epicId = epic;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(int id, String name, Status status, String description, int epic) {
        super(id, name, status, description);
        this.epic = epic;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(int id, String name, Status status, String description, int epic,
                   LocalDateTime startTime, Duration duration) {
        super(id, name, status, description, startTime, duration);
        this.epic = epic;
        this.type = TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
