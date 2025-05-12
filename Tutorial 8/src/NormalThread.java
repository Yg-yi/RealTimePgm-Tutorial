public class NormalThread extends Thread {
    private static final int NUM_THREADS = 10;
    private static final int ITERATIONS = 1000;
    @Override
    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new NormalThread();
            threads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }
        long endTime = System.nanoTime();
        double executionTimeInSeconds = (endTime - startTime) /
                1_000_000_000.0;
        System.out.printf("Normal thread = %.6f seconds%n",
                executionTimeInSeconds);
    }
}