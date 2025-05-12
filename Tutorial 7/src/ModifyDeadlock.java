import java.util.Random;
    public class ModifyDeadlock implements Runnable {
        private static final Object resource1 = new Object();
        private static final Object resource2 = new Object();
        private final Random random = new Random(System.currentTimeMillis());
        public static void main(String[] args) {
            Thread myThread1 = new Thread(new Deadlock(), "thread-1");
            Thread myThread2 = new Thread(new Deadlock(), "thread-2");
            myThread1.start();
            myThread2.start();
        }
        public void run() {
            for (int i = 0; i < 10000; i++) {
                boolean b = random.nextBoolean();
                if (b) {
                    synchronized (resource1) {
                        System.out.println("[" +
                                Thread.currentThread().getName() + "] Locked resource 1.");
                        synchronized (resource2) {
                            System.out.println("[" +
                                    Thread.currentThread().getName() + "] Locked resource 2.");
                            try { Thread.sleep(10); } catch
                            (InterruptedException e) {}
                        }
                    }
                } else {
                    synchronized (resource1) {
                        System.out.println("[" +
                                Thread.currentThread().getName() + "] Locked resource 1.");
                        synchronized (resource2) {
                            System.out.println("[" +
                                    Thread.currentThread().getName() + "] Locked resource 2.");
                            try { Thread.sleep(10); } catch
                            (InterruptedException e) {}
                        }
                    }
                }
            }
        }
    }