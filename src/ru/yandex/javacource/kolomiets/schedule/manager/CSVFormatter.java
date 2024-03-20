package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.historymemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.*;

import java.util.ArrayList;
import java.util.List;

import java.io.*;

public class CSVFormatter {

    /*public static String toStringFromTask(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() +
                "," + task.getDescription() + "," + task.getEpic(); // Без getEpic() раотать не будет
    }

     */

    public static final String COMMA = ",";
    public static String toStringFromTask(Task task) {
        StringBuilder stringTask = new StringBuilder();
        stringTask.append(task.getId()).append(COMMA)
                .append(task.getType()).append(COMMA)
                .append(task.getTitle()).append(COMMA)
                .append(task.getStatus()).append(COMMA)
                .append(task.getDescription()).append(COMMA);
        if (task.getType().equals(TaskType.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            stringTask.append(subtask.getEpicId());
        }
        return stringTask.toString();
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
                return new Task(id, name, status, description);
            case EPIC:
                return new Epic(id, name, status, description);
            case SUBTASK:
                return new Subtask(id, name, status, description, epic);
        }
        return new Task(name, description, status, id);
    }

    public static String historyToString(HistoryManager historyManager) {
        /*ArrayList<Integer> history = new ArrayList<>();
        for(Task task: manager.getHistory()) {
            history.add(task.getId());
        }
        return history.toString();
         */
        final List<Task> history = historyManager.getHistory();
        if (history.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(history.get(0).getId());
        for (int i = 1; i < history.size(); i++) {
            Task task = history.get(i);
            sb.append(",");
            sb.append(task.getId());
        }
        return sb.toString();

    }

    public static List<Integer> historyFromString(String historyStr) throws ManagerSaveException {
        List<Integer> historyOfTasks = new ArrayList<>();
        String[] history = historyStr.split(",");
        for (String h : history) {
            if (!h.isEmpty()) {
                try {
                    historyOfTasks.add(Integer.parseInt(h));
                } catch (NumberFormatException e) {
                    throw new ManagerSaveException("Can't read from file");

                }
            }
        }
        return historyOfTasks;
    }

    public static String getHeader() {
        return ("id,type,name,status,description,epic");
    }
}
