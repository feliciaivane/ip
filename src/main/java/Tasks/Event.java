package Tasks;

public class Event extends Task {
    private String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }
    @Override
    public String printTask() {
        return "[E] " + super.printTask() + " (at: " + at + ")";
    }
}
