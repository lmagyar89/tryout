package com.tryout.concurrent.usejdk.locking;

import com.tryout.concurrent.usejdk.ThreadUtil;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class WeakReentrantLockManager implements LockManager{
    private final Map<String, ReentrantLock> cache = Collections.synchronizedMap(new WeakHashMap<>());

    @Override
    public void lock(String key) {
        System.out.println(Thread.currentThread().getName() + " try lock... " + new Timestamp(System.currentTimeMillis()));
//        cache.computeIfAbsent(key, s -> new ReentrantLock()).lock();
        ReentrantLock lock = cache.computeIfAbsent(key, s -> new ReentrantLock());
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " got lock... " + new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void unlock(String key) {
        ReentrantLock lock = cache.get(key);
        if(lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public void printAfterGC(String key) {
        System.gc();

        System.out.println("After GC: " + cache.get(key));
    }
}
