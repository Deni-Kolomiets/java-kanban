package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.tasks.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public interface TaskManager {
    int addSimple(Task task);

    int addEpic(Epic epic);

    int addSub(Subtask sub);

    List<Task> getTask();

    List<Subtask> getSubtasks();

    List<Epic> getEpic();
    List<Task> getHistory();

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpictask(int id);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);
}
