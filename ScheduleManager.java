import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Optional;
import java.time.LocalTime; // FIX APPLIED: Imported LocalTime

public class ScheduleManager implements Subject {
    // 1. Singleton implementation fields
    private static ScheduleManager instance;
    
    // Core data structure
    private final List<Task> tasks;
    
    // 2. Observer Pattern fields
    private final List<Observer> observers; 

    // Private constructor prevents direct instantiation
    private ScheduleManager() {
        this.tasks = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Public static method to get the single instance (Singleton Pattern)
    public static ScheduleManager getInstance() {
        if (instance == null) {
            synchronized (ScheduleManager.class) {
                if (instance == null) {
                    instance = new ScheduleManager();
                }
            }
        }
        return instance;
    }

    // --- Subject methods (Observer Pattern) ---
    @Override
    public void attach(Observer o) { observers.add(o); }

    @Override
    public void detach(Observer o) { observers.remove(o); } // FIX APPLIED: This method MUST be implemented.

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // --- Core Functionality ---

    public void addTask(Task newTask) throws TaskConflictException {
        for (Task existingTask : tasks) {
            if (newTask.overlapsWith(existingTask)) {
                String conflictMsg = String.format("Conflict detected! Task '%s' (%s-%s) overlaps with existing task '%s' (%s-%s).", 
                                                   newTask.getDescription(), newTask.getStartTime(), newTask.getEndTime(), 
                                                   existingTask.getDescription(), existingTask.getStartTime(), existingTask.getEndTime());
                notifyObservers(conflictMsg); 
                throw new TaskConflictException(conflictMsg);
            }
        }
        tasks.add(newTask);
        notifyObservers(String.format("Task successfully added: '%s'.", newTask.getDescription()));
    }

    public boolean removeTask(String description) {
        // FIX APPLIED: Changed LocalTime.MIN to LocalTime.MAX in the dummy Task creation
        boolean removed = tasks.remove(new Task(description, LocalTime.MIN, LocalTime.MAX, "LOW"));
        if (!removed) {
            throw new IllegalArgumentException("Task '" + description + "' not found in the schedule.");
        }
        notifyObservers(String.format("Task '%s' successfully removed.", description));
        return true;
    }

    public List<Task> viewTasks() {
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        // Sort by start time (Mandatory Requirement 3)
        tasks.sort(Comparator.comparing(Task::getStartTime));
        return tasks;
    }
    
    public void markTaskCompleted(String description) {
        Optional<Task> taskToComplete = tasks.stream()
            .filter(t -> t.getDescription().equalsIgnoreCase(description))
            .findFirst();
            
        if (taskToComplete.isPresent()) {
            taskToComplete.get().markCompleted();
            notifyObservers(String.format("Task '%s' marked as completed.", description));
        } else {
            throw new IllegalArgumentException("Task '" + description + "' not found to mark as completed.");
        }
    }
}