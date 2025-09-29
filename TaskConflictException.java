// Custom checked exception for task overlap conflicts
public class TaskConflictException extends Exception {
    public TaskConflictException(String message) {
        super(message);
    }
}