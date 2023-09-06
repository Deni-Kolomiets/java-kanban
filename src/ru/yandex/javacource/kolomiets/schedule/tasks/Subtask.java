import ru.yandex.javacource.Kolomiets.schedule.manager.tasks.Task;

public class Subtask extends Task {
    protected int epicId;
    public Subtask(String title, String description, String status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
