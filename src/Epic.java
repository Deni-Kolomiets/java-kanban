import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subIdArray = new ArrayList<>();
    protected int id;
    protected String status;
    public Epic(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
