import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskFactory {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Factory Method to create and validate Task objects
    public static Task createTask(String description, String startTimeStr, String endTimeStr, String priority) 
            throws IllegalArgumentException {
        try {
            LocalTime startTime = LocalTime.parse(startTimeStr, TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(endTimeStr, TIME_FORMATTER);

            String upperPriority = priority.toUpperCase();
            if (!upperPriority.equals("HIGH") && !upperPriority.equals("MEDIUM") && !upperPriority.equals("LOW")) {
                throw new IllegalArgumentException("Invalid priority level: " + priority);
            }

            return new Task(description, startTime, endTime, upperPriority);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Please use HH:mm (e.g., 09:30).");
        }
    }
}