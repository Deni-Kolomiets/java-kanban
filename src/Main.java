import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Status;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;
import ru.yandex.javacource.kolomiets.schedule.manager.Managers;


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

        Subtask sub13 = new Subtask("Title sub 13", "description sub 13", Status.NEW, epic1.getId());
        taskManager.addSub(sub13);
        epic1.addSubtaskId(sub13.getId());

        Epic epic2 = new Epic("Title epic 2", "description epic 2", Status.NEW);
        taskManager.addEpic(epic2);
    }
}

