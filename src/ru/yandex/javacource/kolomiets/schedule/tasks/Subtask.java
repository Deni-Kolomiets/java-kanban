package ru.yandex.javacource.kolomiets.schedule.tasks;

public class Subtask extends Task {
    protected int epicId;
    protected int epic;

    /*public Subtask(String title, String description, int epic) {
        //super(title, description);
        this.epic = epic;
        this.epicId = epic;
        this.type = TaskType.SUBTASK;
    }

     */

    public Subtask(int id, String name, Status status, String description, int epic) {
        super(id, name, status, description);
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
