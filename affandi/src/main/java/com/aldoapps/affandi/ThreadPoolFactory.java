package com.aldoapps.affandi;

import android.os.Process;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ThreadPoolFactory {

    private static ThreadPoolFactory INSTANCE;

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final long KEEP_ALIVE_TIME = 60L;

    private ThreadPoolFactory() {
    }

    public ThreadPoolExecutor createBackgroundPool() {
        return new ThreadPoolExecutor(
                AVAILABLE_PROCESSORS * 2,
                AVAILABLE_PROCESSORS * 2,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new BackgroundThreadFactory()
        );
    }

    public static ThreadPoolFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ThreadPoolFactory.class) {
                INSTANCE = new ThreadPoolFactory();
            }
        }
        return INSTANCE;
    }

    private class BackgroundThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(final Runnable runnable) {
            Runnable wrapperRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    } catch (Throwable ignored) {
                    }
                    runnable.run();
                }
            };
            return new Thread(wrapperRunnable);
        }
    }
}
