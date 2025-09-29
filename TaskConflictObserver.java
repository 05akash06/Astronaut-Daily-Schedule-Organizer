// FIX APPLIED: Made the class public to be accessible by ConsoleApp and ScheduleManager.
public class TaskConflictObserver implements Observer {
    private final String observerName;

    public TaskConflictObserver(String observerName) {
        this.observerName = observerName;
    }

    // The update method signature is correct.
    @Override
    public void update(String message) {
        System.out.printf("\n--- [%s] ---\n%s\n--- END ALERT ---\n", observerName, message);
    }
}