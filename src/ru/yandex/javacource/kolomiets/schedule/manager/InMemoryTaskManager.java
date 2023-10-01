package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.Status;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.historymemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int generatorId = 0;

    @Override
    public int addSimple(Task task) {
        int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public int addSub(Subtask sub) {
        int id = ++generatorId;
        sub.setId(id);
        subtasks.put(id, sub);
        return id;
    }

    @Override
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.addTask(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpictask(int id) {
        historyManager.addTask(epics.get(id));
        return epics.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void updateTask(Task task) {
        final int id = task.getId();
        final Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtasks.put(id, subtask);
        updateEpicStatus(epicId);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        savedEpic.setTitle(epic.getTitle());
        savedEpic.setDescription(epic.getDescription());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Status> statusMemory = new ArrayList<>();
        for (Integer colId : epic.getSubtaskIds()) {
            statusMemory.add(subtasks.get(colId).getStatus());
        }
        int check = 0;
        for (Status status : statusMemory) {
            if (status.equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        for (Status status : statusMemory) {
            if (!status.equals(Status.DONE)) {
                check++;
            }
        }
        if (check == 0) {
            epic.setStatus(Status.DONE);
        }
    }
}
