package ru.yandex.javacource.kolomiets.schedule.manager;


import java.io.File;

public class FileBackendTaskManagerTest {
    public static void main(String[] args) {
        File file = new File("CSVFile.txt");

        FileBackendTaskManager taskManager1 = new FileBackendTaskManager(file);

        FileBackendTaskManager taskManager2 = FileBackendTaskManager.loadFromFile(file); // Сравнить 2 таск менеджжера

        if (taskManager1.equals(taskManager2)) {
            System.out.println("хорошо");
        } else {
            System.out.println("не хорошо");
        }
    }
}

