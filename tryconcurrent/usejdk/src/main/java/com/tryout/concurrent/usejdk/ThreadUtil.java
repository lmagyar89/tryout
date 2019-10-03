package com.tryout.concurrent.usejdk;

public class ThreadUtil {
    public static void sleepThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void joinOnThread(Thread threadToJoin) {
        try {
            threadToJoin.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
