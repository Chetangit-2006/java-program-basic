
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {
    private String taskName;

    Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(taskName + " started by "
                + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(taskName + " completed");
    }
}

public class ExecutorServiceDemo {
    public static void main(String[] args) {

        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit multiple tasks
        for (int i = 1; i <= 6; i++) {
            executor.submit(new Task("Task " + i));
        }

        // Stop accepting new tasks
        executor.shutdown();

        System.out.println("All tasks submitted.");
    }
}
