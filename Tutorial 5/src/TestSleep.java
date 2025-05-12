public class TestSleep {
    public static void main(String[] args) {
        int threadCount = 20;
        for (int i = 1; i <= threadCount; i++) {
            Runnable task = new PrintTask();
            Thread t = new Thread(task);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class PrintTask implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("ONE");
            Thread.sleep(100);
            System.out.println("TWO");
            Thread.sleep(100);
            System.out.println("THREE");
            Thread.sleep(100);
            System.out.println("XXXXXXXXXX");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}