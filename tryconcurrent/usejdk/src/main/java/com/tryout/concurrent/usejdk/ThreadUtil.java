package com.tryout.concurrent.usejdk;

public class ThreadUtil {
    public static void sleepThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
