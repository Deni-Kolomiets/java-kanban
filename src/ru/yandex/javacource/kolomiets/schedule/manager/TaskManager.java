package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.List;


public interface TaskManager {
    void addSimple(Task task);

    void addEpic(Epic epic);

    void addSub(Subtask sub);

    List<Task> getTasks();

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

    List<Task> getPrioritizedTasks();

    void addTask(Task task);

}

