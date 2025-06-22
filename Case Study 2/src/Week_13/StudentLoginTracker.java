package Week_13;

import java.util.concurrent.ConcurrentHashMap;

public class StudentLoginTracker {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> loginCounts = new ConcurrentHashMap<>();
        loginCounts.put("student123", 0);
        loginCounts.put("student456", 0);

        Runnable incrementLogin = () -> {
            for (int i = 0; i < 500; i++) {
                loginCounts.compute("student123", (String k, Integer v) -> v + 1);
                loginCounts.compute("student456", (String k, Integer v) -> v + 1);
            }
        };

        Thread t1 = new Thread(incrementLogin);
        Thread t2 = new Thread(incrementLogin);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final login count for student123: " + loginCounts.get("student123"));
        System.out.println("Final login count for student456: " + loginCounts.get("student456"));
    }
}