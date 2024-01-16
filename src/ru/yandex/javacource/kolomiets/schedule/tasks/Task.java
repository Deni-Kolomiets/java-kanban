package ru.yandex.javacource.kolomiets.schedule.tasks;

import java.util.Objects;

public class Task {
    protected int id;
    protected String title;
    protected String description;
    protected Status status;
    protected int epic;
    protected TaskType type;
    protected Status statusOfTask = Status.NEW;

    public Task(String title, String description, int epic) {
        this.title = title;
        this.description = description;
        this.statusOfTask = statusOfTask;
        this.type = TaskType.TASK;
    }

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description, Status status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
        this.type = TaskType.TASK;
    }

    public Task(int id, String name, Status status, String description, int epic) {
        this.id = id;
        this.title = name;
        this.status = status;
        this.description = description;
        this.epic = epic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public TaskType getType() {
        return type;
    }

    public int getEpic() {
        return epic;
    }


    @Override
    public String toString() {
        return "№" + id + " " + title +
                ", описание: " + description +
                ", statusOfTask: " + status;
    }

    public Status getStatusOfTask() {
        return statusOfTask;
    }

    public void setStatusOfTask(Status statusOfTask) {
        this.statusOfTask = statusOfTask;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(title, otherTask.title) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(status, otherTask.status);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (title != null) {
            hash = title.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode();
        }
        if (status != null) {
            hash = hash + status.hashCode();
        }
        return hash;
    }
}
