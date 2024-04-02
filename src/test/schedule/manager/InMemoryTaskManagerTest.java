package schedule.manager;

import org.junit.Test;
import ru.yandex.javacource.kolomiets.schedule.historymemory.InMemoryHistoryManager;
import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Status;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.yandex.javacource.kolomiets.schedule.tasks.Status.NEW;

class InMemoryTaskManagerTest {
    protected InMemoryTaskManager inMemoryTasksManager = new InMemoryTaskManager();

    @Test
    public void testAddSimple() {
        Task task = new Task("title", "description", NEW);
        task.setId(1);
        inMemoryTasksManager.addSimple(task);

        Task retrievedTask = inMemoryTasksManager.getTask(1);

        assertNotNull(retrievedTask);
        assertEquals(task, retrievedTask);
    }

    @Test
    public void testAddEpic() {
        Epic epic = new Epic("title", "description", NEW);
        epic.setId(1);
        inMemoryTasksManager.addEpic(epic);

        Epic retrievedEpic = inMemoryTasksManager.getEpictask(1);

        assertNotNull(retrievedEpic);
        assertEquals(epic, retrievedEpic);
    }

    @Test
    public void testAddSub() {
        Subtask subtask = new Subtask(1, "title", NEW, "description", 1);
        subtask.setId(1);
        inMemoryTasksManager.addSub(subtask);

        Subtask retrievedSubtask = inMemoryTasksManager.getSubtask(1);

        assertNotNull(retrievedSubtask);
        assertEquals(subtask, retrievedSubtask);
    }

    @Test
    public void testGetTasks() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        task1.setId(1);
        inMemoryTasksManager.addSimple(task1);

        Task task2 = new Task("Task 2", "Description 2", NEW);
        task2.setId(2);
        inMemoryTasksManager.addSimple(task2);

        ArrayList<Task> tasksGetter = new ArrayList<Task>(Arrays.asList(task1, task2));

        ArrayList<Task> retrivedTasksGetter = inMemoryTasksManager.getTasks();

        assertNotNull(retrivedTasksGetter);
        assertEquals(tasksGetter, retrivedTasksGetter);
    }

    @Test
    public void testDeleteTask() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        task1.setId(1);
        inMemoryTasksManager.addSimple(task1);

        Task task2 = new Task("Task 2", "Description 2", NEW);
        task2.setId(2);
        inMemoryTasksManager.addSimple(task2);
        inMemoryTasksManager.deleteTask(2);

        assertNull(inMemoryTasksManager.getTask(2));
        assertEquals(1, inMemoryTasksManager.getTasks().size());
    }

    @Test
    public void testDeleteTasks() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        task1.setId(1);
        inMemoryTasksManager.addSimple(task1);

        Task task2 = new Task("Task 2", "Description 2", NEW);
        task2.setId(2);
        inMemoryTasksManager.addSimple(task2);

        inMemoryTasksManager.deleteTasks();

        assertEquals(0, inMemoryTasksManager.getTasks().size());
    }

    @Test
    public void testGetTask() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        task1.setId(1);
        inMemoryTasksManager.addSimple(task1);

        assertEquals(task1, inMemoryTasksManager.getTask(1));
    }

    @Test
    public void testGetHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task 1", "Description 1", NEW);
        historyManager.addTask(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("Task 1", "Description 1", NEW);

        inMemoryTasksManager.addSimple(task);

        Task updatedTask = new Task("Task 1", "Description 1", NEW);

        inMemoryTasksManager.updateTask(updatedTask);

        assertEquals(updatedTask, inMemoryTasksManager.getTask(1));
    }

    @Test
    public void testUpdateStatus() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", Status.NEW, "Description", 1);
        Subtask subtask2 = new Subtask(2, "Subtask 2", Status.DONE, "Description", 1);

        inMemoryTasksManager.addSub(subtask1);
        inMemoryTasksManager.addSub(subtask2);

        Epic epic = new Epic(1, "Epic 1", "Description 1", Status.NEW);
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        inMemoryTasksManager.addEpic(epic);

        inMemoryTasksManager.updateStatus(1);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        Subtask subtask3 = new Subtask(3, "Subtask 3", Status.IN_PROGRESS, "Description", 1);
        inMemoryTasksManager.addSub(subtask3);
        epic.addSubtaskId(3);

        inMemoryTasksManager.updateStatus(1);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtask1.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);

        inMemoryTasksManager.updateStatus(1);

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void testGetPrioritizedTasks() {
        Task task1 = new Task(1, "Task1", NEW, "Description 1", LocalDateTime.of(2024, 1, 1, 10, 0), Duration.ofHours(1));
        Task task2 = new Task(2, "Task2", NEW, "Description 2", LocalDateTime.of(2024, 1, 1, 12, 0), Duration.ofHours(2));
        Subtask subtask1 = new Subtask(3, "Subtask1", NEW, "Subtask Description", 1);

        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(subtask1);

        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();

        assertEquals(3, prioritizedTasks.size());
        assertEquals(subtask1, prioritizedTasks.get(0));
        assertEquals(task1, prioritizedTasks.get(1));
        assertEquals(task2, prioritizedTasks.get(2));
    }


    @Test
    public void testAddTask() {
        Task task1 = new Task(1, "Task1", NEW, "Description 1", LocalDateTime.of(2024, 1, 1, 10, 0), Duration.ofHours(1));
        Task task2 = new Task(2, "Task2", NEW, "Description 2", LocalDateTime.of(2024, 1, 1, 12, 0), Duration.ofHours(2));

        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.addTask(task1);

        assertFalse(taskManager.getPrioritizedTasks().isEmpty());

        taskManager.addTask(task2);
        assertTrue(taskManager.getPrioritizedTasks().contains(task1));
        assertFalse(taskManager.getPrioritizedTasks().contains(task2));

        Task task3 = new Task(3, "Task3", NEW, "Description 3", LocalDateTime.of(2024, 1, 1, 8, 0), Duration.ofHours(2));
        taskManager.addTask(task3);
        assertTrue(taskManager.getPrioritizedTasks().contains(task3));
    }

    @Test
    public void testUpdateEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic(1, "Epic 1", "Description 1", Status.NEW);

        taskManager.updateEpic(epic);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        assertNotNull(epic.getDuration());

        assertEquals("Epic 1", epic.getTitle());
        assertEquals("Description 1", epic.getDescription());
    }

    @Test
    public void testUpdateEpicStatus() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic(1, "Epic 1", "Description 1", Status.NEW);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask(2, "Subtask1", Status.IN_PROGRESS, "Subtask Description", 1);
        Subtask subtask2 = new Subtask(3,"Subtask 2", Status.NEW, "Description of Subtask 2", 1);
        taskManager.addSub(subtask1);
        taskManager.addSub(subtask2);

        epic.getSubtaskIds().add(1);
        epic.getSubtaskIds().add(2);

        taskManager.updateEpicStatus(1);

        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtask2.setStatus(Status.DONE);

        taskManager.updateEpicStatus(1);

        assertEquals(Status.DONE, epic.getStatus());
    }
}