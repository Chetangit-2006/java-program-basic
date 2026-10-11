import java.util.*;
import java.util.concurrent.*;

// Represents a task submitted to the task manager
class Task implements Callable<String> {
    private final int taskId;
    private final String taskName;

    Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    // Runs on a worker thread and returns a result
    @Override
    public String call() throws Exception {
        Thread.sleep(500); // Simulate work
        return "Task " + taskId + " (" + taskName + ") completed by "
                + Thread.currentThread().getName();
    }
}

// Manages tasks using a fixed-size thread pool
public class TaskManager {
    public static void main(String[] args) {
        // Create a pool with three worker threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Store task results in submission order
        List<Future<String>> results = new ArrayList<>();

        try {
            // Submit tasks to the executor
            results.add(executor.submit(new Task(1, "Generate Report")));
            results.add(executor.submit(new Task(2, "Process Data")));
            results.add(executor.submit(new Task(3, "Send Notification")));
            results.add(executor.submit(new Task(4, "Create Backup")));
            results.add(executor.submit(new Task(5, "Update Records")));

            // Retrieve each result; get() waits if necessary
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        } catch (InterruptedException e) {
            // Restore interrupt status if waiting is interrupted
            Thread.currentThread().interrupt();
            System.out.println("Task manager was interrupted.");
        } catch (ExecutionException e) {
            // Handle errors thrown by a task
            System.out.println("Task failed: " + e.getCause().getMessage());
        } finally {
            // Stop accepting new tasks and finish submitted tasks
            executor.shutdown();
        }
    }
}
