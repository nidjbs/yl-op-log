package com.hyl.op.log.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huayuanlin
 * @date 2021/06/07 22:32
 * @desc the class desc
 */
public class CustomThreadFactory implements ThreadFactory {

    private final String prefix;
    private boolean isDaemon;
    private final AtomicInteger created = new AtomicInteger(0);

    public CustomThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public CustomThreadFactory(String prefix, boolean isDaemon) {
        this.prefix = prefix;
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, prefix + "_" + created.getAndIncrement());
        thread.setDaemon(isDaemon);
        return thread;
    }


}
