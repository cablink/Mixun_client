package org.ancode.util;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;


public abstract class ThreadUtil implements Serializable {
	
    private static final long serialVersionUID = 1L;
    protected static boolean blnStarted;
    protected static ExecutorService etsContainer;
    protected static ThreadFactory tdfContainer;

    protected static synchronized ExecutorService getExecutorService() {
        return null != etsContainer ? etsContainer : (etsContainer = Executors.newFixedThreadPool(50,getThreadFactory()));
    }

    protected static synchronized ThreadFactory getThreadFactory() {
        return null != tdfContainer ? tdfContainer : (tdfContainer = Executors.defaultThreadFactory());
    }

    public static Thread newThread(Runnable r) {
        return getThreadFactory().newThread(r);
    }

    public static synchronized void shutdown() {
        if (blnStarted) {
            try {
                getExecutorService().shutdown();
            } finally {
                etsContainer = null;
                blnStarted = false;
            }
        }
    }

    public static synchronized Future<?> submit(Runnable r) {
        //getThreadFactory().newThread(r).start();
        Future<?> f = null;
        try {
            f = getExecutorService().submit(r);
        } finally {
            blnStarted = true;
        }
        return f;
    }
}