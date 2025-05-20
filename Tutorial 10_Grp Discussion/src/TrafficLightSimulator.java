import java.util.concurrent.locks.ReentrantLock;

    public class TrafficLightSimulator {

        private static final ReentrantLock lock = new ReentrantLock();

        private static class TrafficLight extends Thread {
            private final String lightColor;
            private final int duration;

            public TrafficLight(String lightColor, int duration) {
                this.lightColor = lightColor;
                this.duration = duration;
            }

            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        System.out.println("Light: " + lightColor);
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public static void main(String[] args) {
            Thread red = new TrafficLight("RED", 3000);
            Thread yellow = new TrafficLight("YELLOW", 1000);
            Thread green = new TrafficLight("GREEN", 2000);

            red.start();
            yellow.start();
            green.start();
        }
    }
