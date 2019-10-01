package com.tryout.concurrent.usejdk;

import com.tryout.concurrent.usejdk.locking.DemoReentrantLockManager;
import com.tryout.concurrent.usejdk.locking.LockManager;
import com.tryout.concurrent.usejdk.locking.ReentrantLockManager;
import com.tryout.concurrent.usejdk.locking.WeakReentrantLockManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting... " + Main.class.getCanonicalName());
        //demoReentrantLockManager();
        demoWeakLockManager();
        System.out.println("Stopping... " + Main.class.getCanonicalName());

    }

    private static void demoWeakLockManager() {
        WeakReentrantLockManager lockManager = new WeakReentrantLockManager();
        Thread a = new Thread(() -> {
            try {
                lockManager.lock(new String("key"));
                System.out.println("Within 'a' thread.");
            } finally {
                lockManager.unlock(new String("key"));
            }
        }, "a");

        Thread b = new Thread(() -> {
            try {
                lockManager.lock(new String("key"));
                System.out.println("Within 'b' thread.");
            } finally {
                lockManager.unlock(new String("key"));
            }
        }, "b");

        Thread c = new Thread(() -> {
            try {
                lockManager.lock(new String("key"));
                System.out.println("Within 'c' thread.");
            } finally {
                lockManager.unlock(new String("key"));
            }
        }, "c");

        a.start();
        ThreadUtil.sleepThread(1000);
        b.start();
        ThreadUtil.sleepThread(1000);
        c.start();

        ThreadUtil.sleepThread(7000);

        lockManager.printAfterGC(new String("key"));
    }

    private static void demoReentrantLockManager() {
        //        LockManager lockManager = new ReentrantLockManager();
        LockManager lockManager = new DemoReentrantLockManager();
        String key = "key";

        Thread a = new Thread(() -> {
            try {
                lockManager.lock(key);
                System.out.println("Within 'a' thread.");
            } finally {
                lockManager.unlock(key);
            }
        }, "a");

        Thread b = new Thread(() -> {
            try {
                lockManager.lock(key);
                System.out.println("Within 'b' thread.");
            } finally {
                lockManager.unlock(key);
            }
        }, "b");

        Thread c = new Thread(() -> {
            try {
                lockManager.lock(key);
                System.out.println("Within 'c' thread.");
            } finally {
                lockManager.unlock(key);
            }
        }, "c");

        a.start();
        ThreadUtil.sleepThread(1000);
        b.start();
        ThreadUtil.sleepThread(1000);
        c.start();
    }
}
