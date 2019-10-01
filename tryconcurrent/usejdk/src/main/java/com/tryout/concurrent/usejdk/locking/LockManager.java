package com.tryout.concurrent.usejdk.locking;

public interface LockManager {
    public void lock(String key);
    public void unlock(String key);
}
