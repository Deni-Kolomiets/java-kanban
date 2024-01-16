import ru.yandex.javacource.kolomiets.schedule.manager.FileBackendTaskManager;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;
import ru.yandex.javacource.kolomiets.schedule.tasks.Status;
import ru.yandex.javacource.kolomiets.schedule.manager.TaskManager;
import ru.yandex.javacource.kolomiets.schedule.manager.Managers;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        FileBackendTaskManager restoredFileManager = FileBackendTaskManager.loadFromFile(new File("src/CSVFile.txt"));
    }
}
