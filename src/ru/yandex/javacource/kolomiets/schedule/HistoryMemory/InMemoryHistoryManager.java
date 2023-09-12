package ru.yandex.javacource.kolomiets.schedule.HistoryMemory;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> tasks = new ArrayList<>();
    int historyCounter = 0;

    @Override
    public void addToHistory(Task task){
        if(historyCounter <= HistoryManager.MAX_HISTORY_SIZE) {
            tasks.add(historyCounter, task);
            ++historyCounter;
        } else {
            historyCounter = 0;
            tasks.add(historyCounter, task);
        }
        System.out.println(tasks);
    }

    @Override
    public List<Task> getHistory() {
        return tasks;
    }
}
