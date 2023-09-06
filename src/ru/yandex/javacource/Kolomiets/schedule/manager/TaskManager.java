import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    public int addSimple(Task task) {
        int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }


    public int addEpic(Epic epic) {
        int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    public int addSub(Subtask sub) {
        int id = ++generatorId;
        sub.setId(id);
        subtasks.put(id, sub);
        return id;
    }
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpic() {
        return new ArrayList<>(epics.values());
    }
    public void deleteTask(int id) {
        tasks.remove(id);
    }
    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.subIdArray) {
            subtasks.remove(subtaskId);
        }
    }
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id);
        updateEpicStatus(epic.getId());
    }
    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public Subtask getSubtask(int id){
        return subtasks.get(id);
    }
    public Epic getEpictask(int id){
        return epics.get(id);
    }

    public void updateTask(Task task) {
        final int id = task.getId();
        final Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtasks.put(id, subtask);
        updateEpicStatus(epicId);
    }

    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        epic.setSubIdArray(savedEpic.getSubIdArray());
        savedEpic.setTitle(epic.getTitle());
        savedEpic.setDescription(epic.getDescription());
        savedEpic.setSubIdArray(epic.getSubIdArray());
        epic.setSubIdArray(savedEpic.getSubIdArray());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<String> statusMemory = new ArrayList<>();
        for(Integer colId: epic.subIdArray) {
            statusMemory.add(subtasks.get(colId).getStatus());
        }
        int check = 0;
        for(String status: statusMemory){
            if(status.equals("IN PROGRESS")){
                epic.status = "IN PROGRESS";
                return;
            }
        }
        for(String status: statusMemory){
            if (!status.equals("DONE")){
                check++;
            }
        }
        if (check == 0) {
            epic.status = "DONE";
        }
    }
}
