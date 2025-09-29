import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        // Initialize the ScheduleManager using the Singleton Pattern
        ScheduleManager manager = ScheduleManager.getInstance();
        
        // Initialize an Observer and attach it
        TaskConflictObserver observer = new TaskConflictObserver("SYSTEM ALERT");
        manager.attach(observer);

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- 🚀 Astronaut Daily Schedule Organizer ---");
        System.out.println("Welcome, Astronaut! Enter 'h' for help.");

        boolean running = true;
        while (running) {
            System.out.print("\nCommand [A/R/V/M/H/Q] > ");
            String command = scanner.nextLine().trim().toLowerCase();

            try {
                switch (command) {
                    case "a": handleAddTask(manager, scanner); break;
                    case "r": handleRemoveTask(manager, scanner); break;
                    case "v": handleViewTasks(manager); break;
                    case "m": handleMarkCompleted(manager, scanner); break;
                    case "h": displayHelp(); break;
                    case "q": running = false; System.out.println("Schedule Organizer shut down. Have a productive day!"); break;
                    default: System.out.println("Invalid command. Enter 'h' for help.");
                }
            } catch (Exception e) {
                System.err.println("❌ UNEXPECTED ERROR: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void handleAddTask(ScheduleManager manager, Scanner scanner) {
        System.out.println("\n--- Add New Task ---");
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Start Time (HH:mm): ");
        String start = scanner.nextLine();
        System.out.print("End Time (HH:mm): ");
        String end = scanner.nextLine();
        System.out.print("Priority (High/Medium/Low): ");
        String priority = scanner.nextLine();

        try {
            Task newTask = TaskFactory.createTask(desc, start, end, priority);
            manager.addTask(newTask); 
        } catch (IllegalArgumentException | TaskConflictException e) {
            System.err.println("❌ FAILED: " + e.getMessage());
        }
    }

    private static void handleRemoveTask(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter Description of task to REMOVE: ");
        String desc = scanner.nextLine();
        try {
            manager.removeTask(desc);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ FAILED: " + e.getMessage());
        }
    }

    private static void handleViewTasks(ScheduleManager manager) {
        System.out.println("\n--- 📅 Today's Schedule (Sorted by Time) ---");
        List<Task> tasks = manager.viewTasks();
        if (tasks.isEmpty()) {
            System.out.println("The schedule is currently clear.");
        } else {
            tasks.forEach(task -> System.out.println("   " + task));
        }
        System.out.println("-------------------------------------------");
    }

    private static void handleMarkCompleted(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter Description of task to MARK COMPLETED: ");
        String desc = scanner.nextLine();
        try {
            manager.markTaskCompleted(desc);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ FAILED: " + e.getMessage());
        }
    }

    private static void displayHelp() {
        System.out.println("\n--- Available Commands ---");
        System.out.println("  A: Add new task");
        System.out.println("  R: Remove existing task");
        System.out.println("  V: View current schedule (Sorted by start time)");
        System.out.println("  M: Mark task as completed");
        System.out.println("  H: Display this help menu");
        System.out.println("  Q: Quit the application");
        System.out.println("--------------------------");
    }
}