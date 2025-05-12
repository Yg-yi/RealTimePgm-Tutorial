public class SynchronizedThread extends Thread {
    private static final int NUM_THREADS = 10;
    private static final int ITERATIONS = 1000;
    private static final Object lock = new Object();
    @Override
    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
            synchronized (lock) {
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new SynchronizedThread();
            threads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }
        long endTime = System.nanoTime();
        double executionTimeInSeconds = (endTime - startTime) /
                1_000_000_000.0;
        System.out.printf("Synchronized thread = %.6f seconds%n",
                executionTimeInSeconds);
    }
}