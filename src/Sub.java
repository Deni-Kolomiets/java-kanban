public class Sub extends Task {
    protected String status;
    protected int epicId; // это id эпика
    public Sub(String title, String description, String status, int epicId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
