package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExp {
    public static void main(String[] args) {
        ExecutorService executors = null;
        try {
            executors = Executors.newSingleThreadExecutor();
            for(int i = 0; i < 10; i++){
                final int count = i;
                executors.execute(() -> {
                    log.info("Doing task - {}, Thread id - {}", count, Thread.currentThread().getId());
                });
            }
        } finally {
            executors.shutdown();
        }
    }
}
