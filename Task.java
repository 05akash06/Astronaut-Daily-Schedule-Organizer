import java.time.LocalTime;
import java.util.Objects;

public class Task {
    private final String description;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String priority; // High, Medium, Low
    private boolean isCompleted;

    public Task(String description, LocalTime startTime, LocalTime endTime, String priority) {
        // Ensure start is strictly before end (The fix for the removeTask issue uses LocalTime.MAX)
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.isCompleted = false;
    }

    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getDescription() { return description; }

    // Task Overlap Validation Logic
    public boolean overlapsWith(Task other) {
        return other.getStartTime().isBefore(this.endTime) && other.getEndTime().isAfter(this.startTime);
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %s [%s]%s",
                startTime, endTime, description, priority, isCompleted ? " (Completed)" : "");
    }

    // Required for the removeTask method logic in ScheduleManager
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(description.toLowerCase(), task.description.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description.toLowerCase());
    }
}