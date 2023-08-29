import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Simple> simpleMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, Sub> subMap = new HashMap<>();
    public int nextId = 1;

    public void addSimple(Simple simple) {
        simple.setId(nextId);
        simpleMap.put(simple.getId(), simple);
        nextId++;
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epicMap.put(epic.getId(), epic);
        nextId++;
    }

    public void addSub(Sub sub) {
        sub.setId(nextId);
        subMap.put(sub.id, sub);
        nextId++;
    }

    public void printAllTasks() {
        for (Simple simple : simpleMap.values()) {
            System.out.println(simple);
        }
        for (Epic epic : epicMap.values()) {
            System.out.println(epic);
        }
        for (Sub sub : subMap.values()) {
            System.out.println(sub);
        }
    }

    public void deleteAllTasks() {
        simpleMap.clear();
        subMap.clear();
        epicMap.clear();
    }

    public void getById(int id) {
        if (simpleMap.containsKey(id)) {
            System.out.println("id есть в базе " + simpleMap.get(id));
        } else if (subMap.containsKey(id)) {
            System.out.println(subMap.get(id));
        } else if (epicMap.containsKey(id)) {
            System.out.println(epicMap.get(id));
        } else {
            System.out.println("Задачи с таким id нету в базе");
        }
    }

    public void updateSimple(int id, Simple simple) {
        simple.setId(id);
        simpleMap.put(id, simple);
    }

    public void updateSub(int id, Sub sub) {
        sub.setId(id);
        subMap.put(id, sub);
        if(sub.getStatus().equals("IN PROGRESS")) {
            epicMap.get(sub.getEpicId()).status = "IN PROGRESS";
            return;
        }

        ArrayList<String> statusMemory = new ArrayList<>();
        for(Integer colId: epicMap.get(sub.getEpicId()).subIdArray) {
            statusMemory.add(subMap.get(colId).getStatus());
        }
        int check = 0;
        for(String status: statusMemory){
            if (!status.equals("DONE")){
                check++;
            }
        }
        System.out.println("Вывод массива " + statusMemory);;
        if (check == 0) {
            epicMap.get(sub.getEpicId()).status = "DONE";
        }
    }

    public void updateEpic(int id, Epic epic) {
        epic.subIdArray = epicMap.get(id).subIdArray;
        epic.setId(id);
        epicMap.put(id, epic);
    }
}
