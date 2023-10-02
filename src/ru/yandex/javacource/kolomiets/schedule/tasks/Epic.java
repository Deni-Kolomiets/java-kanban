package ru.yandex.javacource.kolomiets.schedule.tasks;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
    }

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
    }


    public void cleanSubtaskIds() {

        subtaskIds.clear();

    }

    public void removeSubtask(int id) {

        subtaskIds.remove(id);
    }

    public ArrayList<Integer> getSubIds() {
        return subtaskIds;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }
}
