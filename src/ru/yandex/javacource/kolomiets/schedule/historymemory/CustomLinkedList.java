package ru.yandex.javacource.kolomiets.schedule.historymemory;

import ru.yandex.javacource.kolomiets.schedule.tasks.Task;


public interface CustomLinkedList {
    void addTask(Task task);
    void remove(int id);
}
