/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.limititer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author quangdt
 */
public class RequestRateLimiter {
    private final Semaphore semaphore;

    private final int maxPermits;

    private final TimeUnit timePeriod;

    private ScheduledExecutorService scheduler;

    private RequestRateLimiter(int maxPermits, TimeUnit timePeriod) {
        this.maxPermits = maxPermits;
        this.timePeriod = timePeriod;
        this.semaphore = new Semaphore(maxPermits);
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void release() {
        semaphore.release();
    }

    public static RequestRateLimiter createLimiter(int maxPermits, TimeUnit timePeriod) {
        RequestRateLimiter limiter = new RequestRateLimiter(maxPermits, timePeriod);
        //Start thread
        limiter.schedulePermitReplenishment();
        return limiter;
    }

    private void schedulePermitReplenishment() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(() -> {
            semaphore.release(maxPermits - semaphore.availablePermits());
        }, 0, 1, timePeriod);
    }
}
