package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ForkJoinExp extends RecursiveTask<Integer> {
    private int start;
    private int end;
    private static final int THRESHOD = 2;
    public ForkJoinExp(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum = 0;
        /**
         * 如果任务已经无法分割，就直接进行运算。
         * 实际上就是判断是否还可以div
         */
        if(end - start <= THRESHOD){
            log.info("thread - {}", Thread.currentThread().getId());
            for(int i = start; i <= end; i++)
                sum += i;
        }else{
            /**
             * 如果任务可以分割，则将任务进行分割，通过递归的方式向下传递。
             */
            int middle = start + (end - start) / 2;
            ForkJoinExp leftTask = new ForkJoinExp(start, middle);
            ForkJoinExp rightTask = new ForkJoinExp(middle + 1, end);
            /**
             * Fork出子任务并执行。
             */
            leftTask.fork();
            rightTask.fork();
            /**
             * 等待子任务返回，并将结果进行合并。
             */
            sum += leftTask.join() + rightTask.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = null;
        try {
            /**
             * Fork/Join框架的代码一定要通过ForkJoinPool进行运算。
             */
            pool = new ForkJoinPool();
            log.info("Result {}", pool.submit(new ForkJoinExp(1, 100)).get());
        }finally {
            /**
             * 将pool资源释放。
             */
            pool.shutdown();
        }
    }
}
