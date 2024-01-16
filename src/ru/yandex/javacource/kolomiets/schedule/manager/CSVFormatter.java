package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.historymemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.*;

import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {

    public static String toStringFromTask(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() +
                "," + task.getDescription() +
                "," + task.getEpic();
    }
    public static Task fromStringToTask(String taskStr) {
        String[] tokens = taskStr.split(",");
        int id = Integer.parseInt(tokens[0]);
        TaskType taskType = TaskType.valueOf(tokens[1]);
        String name = tokens[2];
        Status status = Status.valueOf(tokens[3]);
        String description = tokens[4];
        int epic = 0;
        if (tokens.length == 6) {
            epic = Integer.parseInt(tokens[5]);
        }

        switch(taskType) {
            case TASK:
                return new Task(id, name, status, description, epic);
            case EPIC:
                return new Epic(id, name, status, description, epic);
            case SUBTASK:
                return new Subtask(id, name, status, description, epic);
        }
        return new Task(name, description, status, id);
    }

    public static String historyToString(HistoryManager manager) {
        ArrayList<Integer> history = new ArrayList<>();
        for(Task task: manager.getHistory()) {
            history.add(task.getId());
        }
        return history.toString();
    }

    public static List<Integer> historyFromString(String historyStr) {
        List<Integer> historyOfTasks = new ArrayList<>();
        String[] history = historyStr.split(",");
        for (String h : history) {
            if (!h.isEmpty()) {
                try {
                    historyOfTasks.add(Integer.parseInt(h));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка парсинга: " + e.getMessage());
                }
            }
        }
        return historyOfTasks;
    }
}
