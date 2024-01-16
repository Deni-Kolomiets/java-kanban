package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.historymemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.historymemory.InMemoryHistoryManager;
import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
