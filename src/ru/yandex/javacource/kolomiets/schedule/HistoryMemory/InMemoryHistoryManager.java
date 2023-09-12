package ru.yandex.javacource.kolomiets.schedule;
// import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;

import java.util.ArrayList;
import java.util.List;
public class InMemoryHistoryManager<Task> {
    private final List<Task> tasks = new ArrayList<>();
    int historyCounter = 0;

    public void addToHistory(Task task){
        if(historyCounter <= 10) {
            tasks.add(historyCounter, task);
            ++historyCounter;
        } else {
            historyCounter = 0;
            tasks.add(historyCounter, task);
        }
        System.out.println(tasks);
    }

    public List<Task> getHistory() {
        return tasks;
    }
}
