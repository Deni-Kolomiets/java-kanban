package ru.yandex.javacource.kolomiets.schedule.historymemory;

import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    List<Task> getHistory();

    void remove(int id);
}
