import ru.yandex.javacource.kolomiets.schedule.manager.FileBackendTaskManager;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        FileBackendTaskManager restoredFileManager = FileBackendTaskManager.loadFromFile(new File("src/ru/yandex/javacource/kolomiets/schedule/resources/CSVFile.csv"));
    }
}
