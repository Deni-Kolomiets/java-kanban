package ru.yandex.javacource.kolomiets.schedule.historymemory;

import org.junit.Test;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.javacource.kolomiets.schedule.tasks.Status.NEW;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> history = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        int id = task.getId();
        remove(id);
        linkLast(task);
        history.put(id, last);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = first;

        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }

        return tasks;
    }

    @Override
    public void remove(int id) {
        Node node = history.remove(id);
        if (node == null) {
            return;
        }

        removeNode(node);
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        }
    }

    private void linkLast(Task task) {
        Node node = new Node(task, last, null);

        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    public static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    public static class HistoryManagerTest {
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
}

