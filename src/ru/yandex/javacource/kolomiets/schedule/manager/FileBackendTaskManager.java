package ru.yandex.javacource.kolomiets.schedule.manager;

import ru.yandex.javacource.kolomiets.schedule.historymemory.*;

import ru.yandex.javacource.kolomiets.schedule.tasks.*;
import ru.yandex.javacource.kolomiets.schedule.tasks.Epic;
import ru.yandex.javacource.kolomiets.schedule.tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.javacource.kolomiets.schedule.manager.CSVFormatter.historyFromString;

public class FileBackendTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackendTaskManager(File fileName) {
        super();
        this.file = fileName;
    }
    /*
    public static FileBackendTaskManager loadFromFile (File file) {
        TaskManager newTaskmanager = new FileBackendTaskManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (skipFirstLine == true) {
                    skipFirstLine = false;
                    continue;
                }

                if (!line.isBlank()) {
                    Task task = CSVFormatter.fromStringToTask(line);
                    if (task != null && task.getType() == TaskType.TASK) {
                        newTaskmanager.addSimple(task);
                    } else if (task != null && task.getType() == TaskType.SUBTASK) {
                        newTaskmanager.addSub((Subtask) task);
                    } else if (task != null && task.getType() == TaskType.EPIC) {
                        newTaskmanager.addEpic((Epic) task);
                    }
                }

                if (line.isBlank()) {
                    line = br.readLine();
                    List<Integer> historyNumbers = historyFromString(line);
                    for (Integer taskId : historyNumbers) {
                        if (newTaskmanager.getTasks().get(taskId) != null) {
                            ((FileBackendTaskManager) newTaskmanager).historyManager.addTask(newTaskmanager.getTasks().get(taskId)); // Проверить эти методы на правильность того что возвращают таблицу
                        } else if (newTaskmanager.getEpic().get(taskId) != null) {
                            ((FileBackendTaskManager) newTaskmanager).historyManager.addTask(newTaskmanager.getEpic().get(taskId));
                        } else if (newTaskmanager.getSubtasks().get(taskId) != null) {
                            ((FileBackendTaskManager) newTaskmanager).historyManager.addTask(newTaskmanager.getSubtasks().get(taskId)); // newTaskmanager.addTask()
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (FileBackendTaskManager) newTaskmanager;
    }

     */
    public static FileBackendTaskManager loadFromFile(File file) {
        FileBackendTaskManager newTaskmanager = new FileBackendTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (skipFirstLine == true) {
                    skipFirstLine = false;
                    continue;
                }

                if (!line.isBlank()) {
                    Task task = CSVFormatter.fromStringToTask(line);
                    if (task != null && task.getType() == TaskType.TASK) {
                        newTaskmanager.addSimple(task);
                    } else if (task != null && task.getType() == TaskType.SUBTASK) {
                        newTaskmanager.addSub((Subtask) task);
                    } else if (task != null && task.getType() == TaskType.EPIC) {
                        newTaskmanager.addEpic((Epic) task);
                    }
                }

                if (line.isBlank()) {
                    line = br.readLine();
                    List<Integer> historyNumbers = historyFromString(line);
                    for (Integer taskId : historyNumbers) {
                        List<Task> taskList = newTaskmanager.getTasks();
                        if (taskId < taskList.size() && taskList.get(taskId) != null) {
                            newTaskmanager.historyManager.addTask(taskList.get(taskId));
                        } else {
                            List<Epic> epicList = newTaskmanager.getEpic();
                            if (taskId < epicList.size() && epicList.get(taskId) != null) {
                                newTaskmanager.historyManager.addTask(epicList.get(taskId));
                            } else {
                                List<Subtask> subtaskList = newTaskmanager.getSubtasks();
                                if (taskId < subtaskList.size() && subtaskList.get(taskId) != null) {
                                    newTaskmanager.historyManager.addTask(subtaskList.get(taskId));
                                }
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newTaskmanager;
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
            writer.write("id,type,name,status,description,epic\n");

            CSVFormatter formatter = new CSVFormatter();

            List<Task> tasks = new ArrayList<>(super.getTasks());
            for (Task task : tasks) {
                writer.write(formatter.toStringFromTask(task) + "\n");
            }

            List<Epic> epics = new ArrayList<>(super.getEpic());
            for (Epic epic : epics) {
                writer.write(formatter.toStringFromTask(epic) + "\n");
            }

            List<Subtask> subtasks = new ArrayList<>(super.getSubtasks());
            for (Subtask subtask : subtasks) {
                writer.write(formatter.toStringFromTask(subtask) + "\n");
            }

            writer.write("\n");

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
            //if (this.historyManager != null) {   Старый варинат 08.01 вечер
            //    String history = CSVFormatter.historyToString(this.historyManager);
            //    writer.write(history);
            //}
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка записи файла.");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
