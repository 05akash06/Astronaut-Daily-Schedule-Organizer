#  Astronaut Daily Schedule Organizer

This is a simple, console-based Java application designed to manage an astronaut's daily tasks. It implements core CRUD (Create, Read, Update, Delete) functionality with strong error handling, especially for time conflicts.

## Key Design Patterns

The application uses three key design patterns to ensure robust, clean, and maintainable code

1.  **Singleton Pattern**: Used in `ScheduleManager` to ensure only one instance of the schedule exists.
2.  **Factory Pattern**: Used in `TaskFactory` to handle task creation and centralize input validation.
3.  **Observer Pattern**: Used to alert the user immediately when a task conflict is detected.

## How to Run the Application

### Prerequisites

* Java Development Kit (JDK) installed.

### Execution

1.  **Clone the repository** (or download the files).
2.  **Navigate** to the project folder (`AstronautScheduler`) in your terminal.
3.  **Compile** all Java files:
    ```bash
    javac *.java
    ```
4.  **Run** the main application:
    ```bash
    java ConsoleApp
    ```

## Usage Commands

The application provides an interactive menu:

* **A**: Add new task (Requires description, start time, end time, and priority)
* **R**: Remove existing task
* **V**: View current schedule (Tasks are sorted by time)
* **M**: Mark task as completed
* **Q**: Quit the application
