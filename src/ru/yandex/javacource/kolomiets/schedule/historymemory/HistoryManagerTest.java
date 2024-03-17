package ru.yandex.javacource.kolomiets.schedule.historymemory;

import org.junit.Test;

import ru.yandex.javacource.kolomiets.schedule.tasks.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.javacource.kolomiets.schedule.tasks.Status.NEW;

public class HistoryManagerTest {
    private HistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    public void testEmptyHistory() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    public void testDuplicateTask() {
        Task task = new Task(1, "Task 1", NEW, "Description 1");
        historyManager.addTask(task);
        historyManager.addTask(task);

        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    public void testRemoveFromHistory() {
        Task task1 = new Task(1, "Task 1",  NEW, "Description 1");
        Task task2 = new Task(2, "Task 2",  NEW, "Description 2");
        Task task3 = new Task(3, "Task 3",  NEW, "Description 3");

        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);

        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());

        historyManager.remove(2);
        assertEquals(1, historyManager.getHistory().size());

        historyManager.remove(3);
        assertTrue(historyManager.getHistory().isEmpty());
    }
}

