import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new TaskManager();

        Task simpleTask1 = new Task("Title simple 1", "description simple 1", "NEW");
        manager.addSimple(simpleTask1);
        Task simpleTask2 = new Task("Title simple 2", "description simple 2", "NEW");
        manager.addSimple(simpleTask2);
        System.out.println(simpleTask1 + " " + simpleTask2);


        Epic epic1 = new Epic("Title epic 1", "description epic 1", "NEW");
        manager.addEpic(epic1);

        Subtask sub11 = new Subtask("Title sub 1", "description sub 1", "NEW", epic1.getId());
        manager.addSub(sub11);
        epic1.addSubtaskId(sub11.getId());

        Subtask sub12 = new Subtask("Title sub 12", "description sub 12", "NEW", epic1.getId());
        manager.addSub(sub12);
        epic1.addSubtaskId(sub12.getId());

        Epic epic2 = new Epic("Title epic 2", "description epic 2", "NEW");
        manager.addEpic(epic2);
        System.out.println(epic2.getStatus() + " " + epic2.getId()); // +

        Subtask sub21 = new Subtask("Title sub 21", "description sub 21", "NEW", epic2.getId());
        manager.addSub(sub21);
        epic2.addSubtaskId(sub21.getId());

        Task newTask1 = new Task("Title newSimple 1", "description newSimple 1", "IN PROGRESS", simpleTask1.getId());
        manager.updateTask(newTask1);
        System.out.println(newTask1.getId());

        Subtask newSub11 = new Subtask("Title newSub 1", "description newSub 11", "DONE", epic1.getId());
        manager.updateSubtask(newSub11);

        Subtask newSub12 = new Subtask("Title newSub 12", "description newSub 12", "DONE", epic1.getId());
        manager.updateSubtask(newSub12);
    }
}
