package com.videoconference.core.Stream;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VideoStreamSimulator implements Runnable {
    private final String userEmail;
    private boolean running = true;
    private final Lock lock = new ReentrantLock();

    public VideoStreamSimulator(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void run() {
        int frame = 1;
        try {
            while (running) {
                // Simulate sending/receiving a video frame every second
                System.out.println("[VideoStream] " + userEmail + " - Sending frame " + frame++);
                Thread.sleep(1000); // 1 frame per second
            }
        } catch (InterruptedException e) {
            System.out.println("[VideoStream] " + userEmail + " - Video stream stopped.");
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }

    public void stop() { running = false; }

    public void criticalSection() {
        lock.lock();
        try {
            // critical section
        } finally {
            lock.unlock();
        }
    }
}
