package ru.yandex.javacource.kolomiets.schedule.tasks;

import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
    }

    public Epic(int id, String title, String description, Status status) {
        super(title, description, status);
    }

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
        this.type = TaskType.EPIC;
    }

    public Epic(int id, String name, Status status, String description, LocalDateTime startTime, Duration duration) {
        super(id, name, status, description,startTime, duration);
        this.type = TaskType.EPIC;
    }

    public LocalDateTime getEndTime() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        for(int id: this.getSubtaskIds()) {
            Subtask subtask = inMemoryTaskManager.getSubtask(id);
            int comparisonStart = subtask.getStartTime().compareTo(startTime);
            if(comparisonStart < 0) {
                startTime = subtask.getStartTime();
            }

            int comparisonEnd = subtask.getEndTime().compareTo(endTime);
            if(comparisonEnd > 0) {
                endTime = subtask.getEndTime();
            }
        }
        return endTime;
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

    public void setEndTime(LocalDateTime end) {
        this.endTime = end;
    }
}
