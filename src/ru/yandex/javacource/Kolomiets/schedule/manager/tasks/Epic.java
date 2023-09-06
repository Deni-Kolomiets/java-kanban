import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subIdArray = new ArrayList<>();

    public Epic(String title, String description, String status) {
        super(title,description,status);
    }

    public Epic(String title, String description, String status, int id) {
        super(title,description,status, id);
    }

    public void cleanSubtaskIds(){
        subIdArray.clear();
    }
    public void removeSubtask(int id){
        subIdArray.remove(id);
    }
    public ArrayList<Integer> getSubIds() {
        return subIdArray;
    }

    public ArrayList<Integer> getSubIdArray() {
        return subIdArray;
    }

    public void setSubIdArray(ArrayList<Integer> subIdArray) {
        this.subIdArray = subIdArray;
    }
}
