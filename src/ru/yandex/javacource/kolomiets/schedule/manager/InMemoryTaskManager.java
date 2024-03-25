package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.historymemory.HistoryManager;
import ru.yandex.javacource.kolomiets.schedule.historymemory.InMemoryHistoryManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Status;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int numberOfTask = 0;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    HistoryManager historyManager = new InMemoryHistoryManager();

    TreeSet<Task> prioritizedTasks = (TreeSet<Task>) getPrioritizedTasks();

    private int generatorId = 0;


    @Override
    public void addSimple(Task task) {
        if (task.getId() != 0) {
            tasks.put(task.getId(), task);
        } else {
            task.setId(getNumberOfTask());
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getId() != 0) {
            epics.put(epic.getId(), epic);
        } else {
            epic.setId(getNumberOfTask());
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void addSub(Subtask subtask) {
        if (subtask.getId() != 0) {
            subtasks.put(subtask.getId(), subtask);
        } else {
            subtask.setId(getNumberOfTask());
            subtasks.put(subtask.getId(), subtask);
        }
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtaskId(subtask.getId());
        }
        updateStatus(subtask.getEpicId());
    }

    @Override
    public ArrayList<Task> getTasks() {
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

    @Override
    public List<Task> getPrioritizedTasks() {
        TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

        tasks.values().stream()
                .filter(task -> task.getStartTime() != null)
                .forEach(prioritizedTasks::add);

        subtasks.values().stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .forEach(prioritizedTasks::add);

        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public void addTask(Task task) {
        if (prioritizedTasks.stream().anyMatch(existingTask -> existingTask.isOverlapping(task))) {
            System.out.println("Время выполнения пересекается, невозможно добавить задачу.");
        } else {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
            System.out.println("Задача добавлена успешно.");
        }
    }

    void updateEpicStatus(int epicId) {
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

    void updateStatus(Integer epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            boolean containsDone = false;
            boolean containsNew = false;
            boolean containsInProgress = false;

            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    if (subtask.getStatus().equals(Status.NEW)) {
                        containsNew = true;
                    } else if (subtask.getStatus().equals(Status.DONE)) {
                        containsDone = true;
                    } else {
                        containsInProgress = true;
                    }
                }
            }

            if (containsInProgress || containsNew && containsDone) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (containsNew && !containsDone && !containsInProgress) {
                epic.setStatus(Status.NEW);
            } else if (containsDone && !containsNew && !containsInProgress) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.NEW);
            }
        }
    }

    private int getNumberOfTask() {
        numberOfTask++;
        return numberOfTask;
    }
}
