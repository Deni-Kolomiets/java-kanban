package ru.yandex.javacource.kolomiets.schedule.historymemory;

import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.List;

public interface HistoryManager {
    static final int MAX_HISTORY_SIZE = 10;

    void addTask(Task task);

    List<Task> getHistory();
}
