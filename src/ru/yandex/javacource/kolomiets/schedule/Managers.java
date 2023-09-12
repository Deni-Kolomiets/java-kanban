package ru.yandex.javacource.kolomiets.schedule;
import ru.yandex.javacource.kolomiets.schedule.HistoryMemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.HistoryMemory.InMemoryHistoryManager;
import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager () {
        return new InMemoryHistoryManager();
    }

}
