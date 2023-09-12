package ru.yandex.javacource.kolomiets.schedule.historyMemory;

import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.List;

public interface HistoryManager {
    static final int MAX_HISTORY_SIZE = 10;
    void addToHistory(Task task);
    List<Task> getHistory();
}
