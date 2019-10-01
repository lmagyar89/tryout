package com.tryout.concurrent.usejdk.locking;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockManager implements LockManager{
    private final ConcurrentHashMap<String, ReentrantLock> cache = new ConcurrentHashMap<>();

    @Override
    public void lock(String key) {
        System.out.println(Thread.currentThread().getName() + " try lock... " + new Timestamp(System.currentTimeMillis()));
//        cache.computeIfAbsent(key, s -> new ReentrantLock()).lock();
        ReentrantLock lock = cache.computeIfAbsent(key, s -> new ReentrantLock());
        demonstrateLockingIssue();
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " got lock... " + new Timestamp(System.currentTimeMillis()));
    }

    protected void demonstrateLockingIssue() {

    }

    @Override
    public void unlock(String key) {
        ReentrantLock lock = cache.get(key);
        if(lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
            if(!lock.isLocked()
                    && !lock.hasQueuedThreads()
            ) {
                cache.remove(key);
                System.out.println(Thread.currentThread().getName() + " remove lock from cache: " + lock + new Timestamp(System.currentTimeMillis()));
            }
        }
    }
}
