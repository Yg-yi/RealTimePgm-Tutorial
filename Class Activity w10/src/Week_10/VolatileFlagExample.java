package Week10;

public class VolatileFlagExample {

    // Shared flag between threads
    private static volatile boolean running = true;

    public static void main(String[] args) {
        // Thread that runs continuously until 'running' becomes false
        Thread worker = new Thread(() -> {
            System.out.println("Worker thread started...");
            while (running) {
                // simulate work
            }
            System.out.println("Worker thread stopped.");
        });

        worker.start();

        // Main thread sleeps for a bit then signals stop
        try {
            Thread.sleep(2000); // Let the worker run for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}

