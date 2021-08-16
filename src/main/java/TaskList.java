import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public String getList() {
        String result = "Here's your tasks! Wow I'm so helpful! \n";
        for (int index = 0; index < this.taskList.size(); index++) {
            result = result + (index + 1) + "." + this.taskList.get(index).printTask() + "\n";
        }
        return result;
    }

    public String markIndexCompleted(int index) {
        return this.taskList.get(index).markCompleted();
    }
}
