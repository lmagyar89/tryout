package com.tryout.concurrent.usejdk.locking;

import com.tryout.concurrent.usejdk.ThreadUtil;

public class DemoReentrantLockManager extends ReentrantLockManager {
    @Override
    protected void demonstrateLockingIssue() {
        ThreadUtil.sleepThread(5000);
    }
}
