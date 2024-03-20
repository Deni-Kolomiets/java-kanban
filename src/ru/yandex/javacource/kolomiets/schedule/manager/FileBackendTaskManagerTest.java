package ru.yandex.javacource.kolomiets.schedule.manager;


import java.io.File;

public class FileBackendTaskManagerTest {
    public static void main(String[] args) {
        File file = new File("ru/yandex/javacource/kolomiets/schedule/resources/CSVFile.csv");

        //FileBackendTaskManager taskManager1 = new FileBackendTaskManager(file);
        TaskManager manager = Managers.getDefault();

        FileBackendTaskManager taskManager1 = FileBackendTaskManager.loadFromFile(file);

        FileBackendTaskManager taskManager2 = FileBackendTaskManager.loadFromFile(file);

        if (taskManager1.equals(taskManager2)) {
            System.out.println("хорошо");
        } else {
            System.out.println("не хорошо");
        }
    }
}

