import ru.yandex.javacource.kolomiets.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.Status;
import ru.yandex.javacource.kolomiets.schedule.historyMemory.InMemoryHistoryManager;


public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


        Task simpleTask1 = new Task("Title simple 1", "description simple 1", Status.NEW);
        inMemoryTaskManager.addSimple(simpleTask1);
        Task simpleTask2 = new Task("Title simple 2", "description simple 2", Status.NEW);
        inMemoryTaskManager.addSimple(simpleTask2);


        Epic epic1 = new Epic("Title epic 1", "description epic 1", Status.NEW);
        inMemoryTaskManager.addEpic(epic1);

        Subtask sub11 = new Subtask("Title sub 1", "description sub 1", Status.NEW, epic1.getId());
        inMemoryTaskManager.addSub(sub11);
        epic1.addSubtaskId(sub11.getId());

        Subtask sub12 = new Subtask("Title sub 12", "description sub 12", Status.NEW, epic1.getId());
        inMemoryTaskManager.addSub(sub12);
        epic1.addSubtaskId(sub12.getId());

        Epic epic2 = new Epic("Title epic 2", "description epic 2", Status.NEW);
        inMemoryTaskManager.addEpic(epic2);

        Subtask sub21 = new Subtask("Title sub 21", "description sub 21", Status.NEW, epic2.getId());
        inMemoryTaskManager.addSub(sub21);
        epic2.addSubtaskId(sub21.getId());


        Task newTask1 = new Task("Title newSimple 1", "description newSimple 1", Status.IN_PROGRESS, simpleTask1.getId());
        inMemoryTaskManager.updateTask(newTask1);

        Subtask newSub11 = new Subtask("Title newSub 1", "description newSub 11", Status.DONE, epic1.getId());
        inMemoryTaskManager.updateSubtask(newSub11);

        Subtask newSub12 = new Subtask("Title newSub 12", "description newSub 12", Status.DONE, epic1.getId());
        inMemoryTaskManager.updateSubtask(newSub12);

        inMemoryHistoryManager.addToHistory(epic2);
        inMemoryHistoryManager.addToHistory(epic1);
        inMemoryHistoryManager.addToHistory(newSub12);

        for (Task task: inMemoryHistoryManager.getHistory()){
            if(task != null) {
                System.out.println(task.getTitle());
            }
        }
    }
}

