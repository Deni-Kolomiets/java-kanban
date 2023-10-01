package ru.yandex.javacource.kolomiets.schedule.historymemory;

import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager, CustomLinkedList {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
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
        getHistory();
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
    public void remove(int id) { // Этот метод перепроверить
        Node node = nodeMap.remove(id);
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
}
