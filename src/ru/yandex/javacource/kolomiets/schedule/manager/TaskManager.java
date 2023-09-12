package ru.yandex.javacource.kolomiets.schedule;
import ru.yandex.javacource.kolomiets.schedule.tasks.*;
import java.util.ArrayList;


public interface TaskManager {
    int addSimple(Task task);
    int addEpic(Epic epic);
    int addSub(Subtask sub);
    ArrayList<Task> getTask();
    ArrayList<Subtask> getSubtasks();
    ArrayList<Epic> getEpic();
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
