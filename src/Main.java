import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.Status;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;
import ru.yandex.javacource.kolomiets.schedule.Managers;


public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();


        Task simpleTask1 = new Task("Title simple 1", "description simple 1", Status.NEW);
        taskManager.addSimple(simpleTask1);
        Task simpleTask2 = new Task("Title simple 2", "description simple 2", Status.NEW);
        taskManager.addSimple(simpleTask2);


        Epic epic1 = new Epic("Title epic 1", "description epic 1", Status.NEW);
        taskManager.addEpic(epic1);

        Subtask sub11 = new Subtask("Title sub 1", "description sub 1", Status.NEW, epic1.getId());
        taskManager.addSub(sub11);
        epic1.addSubtaskId(sub11.getId());

        Subtask sub12 = new Subtask("Title sub 12", "description sub 12", Status.NEW, epic1.getId());
        taskManager.addSub(sub12);
        epic1.addSubtaskId(sub12.getId());

        Epic epic2 = new Epic("Title epic 2", "description epic 2", Status.NEW);
        taskManager.addEpic(epic2);

        Subtask sub21 = new Subtask("Title sub 21", "description sub 21", Status.NEW, epic2.getId());
        taskManager.addSub(sub21);
        epic2.addSubtaskId(sub21.getId());


        Task newTask1 = new Task("Title newSimple 1", "description newSimple 1", Status.IN_PROGRESS, simpleTask1.getId());
        taskManager.updateTask(newTask1);

        Subtask newSub11 = new Subtask("Title newSub 1", "description newSub 11", Status.DONE, epic1.getId());
        taskManager.updateSubtask(newSub11);

        Subtask newSub12 = new Subtask("Title newSub 12", "description newSub 12", Status.DONE, epic1.getId());
        taskManager.updateSubtask(newSub12);
    }
}

