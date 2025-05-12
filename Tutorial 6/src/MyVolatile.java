class MyThread extends Thread {
    private volatile boolean running = true;
    public void run() {
        while (running) {
            System.out.println("Thread is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread has stopped.");
    }
    public void shutdown() {
        running = false;
    }
}
public class MyVolatile {
    public static void main(String[] args) throws java.io.IOException {
        MyThread t = new MyThread();
        t.start();
        System.out.println("Press ENTER to stop the thread...");
        System.in.read();
        t.shutdown();
    }
}