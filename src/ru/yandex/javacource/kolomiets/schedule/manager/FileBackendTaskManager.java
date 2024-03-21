package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.ManagerSaveException;
import ru.yandex.javacource.kolomiets.schedule.manager.CSVFormatter;
//import ru.yandex.javacource.kolomiets.schedule.tasks.*;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;
import ru.yandex.javacource.kolomiets.schedule.tasks.Subtask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.javacource.kolomiets.schedule.manager.CSVFormatter.historyFromString;

public class FileBackendTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackendTaskManager(File fileName) {
        super();
        this.file = fileName;
    }


    public static FileBackendTaskManager loadFromFile(File file) {
        FileBackendTaskManager newTaskManager = new FileBackendTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }

                if (!line.isBlank()) {
                    Task task = CSVFormatter.fromStringToTask(line);
                    if (task.getType() == TaskType.TASK) {
                        newTaskManager.addSimple(task);
                    } else if (task.getType() == TaskType.SUBTASK) {
                        newTaskManager.addSub((Subtask) task);
                    } else if (task.getType() == TaskType.EPIC) {
                        newTaskManager.addEpic((Epic) task);
                    }
                }

                if (line.isBlank()) {
                    line = br.readLine();
                    List<Integer> historyNumbers = historyFromString(line);
                    for (Integer taskId : historyNumbers) {
                        List<Task> taskList = newTaskManager.getTasks();
                        if (taskId < taskList.size() && taskList.get(taskId) != null) {
                            newTaskManager.historyManager.addTask(taskList.get(taskId));
                        } else {
                            List<Epic> epicList = newTaskManager.getEpic();
                            if (taskId < epicList.size() && epicList.get(taskId) != null) {
                                newTaskManager.historyManager.addTask(epicList.get(taskId));
                            } else {
                                List<Subtask> subtaskList = newTaskManager.getSubtasks();
                                if (taskId < subtaskList.size() && subtaskList.get(taskId) != null) {
                                    newTaskManager.historyManager.addTask(subtaskList.get(taskId));
                                }
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read from file: " + file.getName());
        }
        return newTaskManager;
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSimple(Task task) {
        super.addSimple(task);
        save();
    }

    @Override
    public void addSub(Subtask sub) {
        super.addSub(sub);
        save();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public ArrayList<Epic> getEpic() {
        return super.getEpic();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return super.getSubtask(id);
    }

    @Override
    public Epic getEpictask(int id) {
        return super.getEpictask(id);
    }

    public void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write(CSVFormatter.getHeader());
            writer.write(System.lineSeparator());

            List<Task> tasks = new ArrayList<>(super.getTasks());
            for (Task task : tasks) {
                writer.write(CSVFormatter.toStringFromTask(task) + System.lineSeparator());
            }

            List<Epic> epics = new ArrayList<>(super.getEpic());
            for (Epic epic : epics) {
                writer.write(CSVFormatter.toStringFromTask(epic) + System.lineSeparator());
            }

            List<Subtask> subtasks = new ArrayList<>(super.getSubtasks());
            for (Subtask subtask : subtasks) {
                writer.write(CSVFormatter.toStringFromTask(subtask) + System.lineSeparator());
            }

            writer.write("\n");

            historyManagerApp();

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла.");
        }
    }

    public void historyManagerApp() {
        try (Writer writer = new FileWriter(file)) {
            if (this.historyManager != null) {
                List<Integer> history = new ArrayList<>();
                for (Task task : this.historyManager.getHistory()) {
                    history.add(task.getId());
                }
                StringBuilder historyStrBuilder = new StringBuilder();
                for (int i = 0; i < history.size(); i++) {
                    historyStrBuilder.append(history.get(i));
                    if (i < history.size() - 1) {
                        historyStrBuilder.append(",");
                    }
                }
                String historyStr = historyStrBuilder.toString();
                writer.write(historyStr + "\n");
            }
            if (this.historyManager != null) {
                String history = CSVFormatter.historyToString(this.historyManager);
                writer.write(history);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла.");
        }
    }

}

