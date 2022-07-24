package com.dfedorino.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MultiThreadedGCPractice {
    // >> Pay attention to the JVM version and default gc

    /**
     * Task:    understand, how the number of threads holding an object of 800kb
     *          impacts the performance of the GC.
     *
     * Process: create n tasks and launch them simultaneously with n threads
     *
     * Env:     OpenJDK 1.8 -Xmx1024m -Xms1024m -verbose:gc, CMS GC
     *          Young Gen=567m
     *              Eden Space=340m
     *              S0=113.5m
     *              S1=113.5m
     *          Old Gen=683m
     *
     *
     * Results:
     * 10 users make 1 request simultaneously:
     * */


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numberOfSeconds = 10;
        int numberOfUsers = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
        List<Callable<Void>> tasks = new ArrayList<>(numberOfUsers);
        for (int j = 0; j < numberOfUsers; j++) {
            tasks.add(new Task());
        }

        // process requests
        for (int i = 0; i < numberOfSeconds; i++) {
            executorService.invokeAll(tasks);
            Thread.sleep(1000);
        }

        executorService.shutdown();
        executorService.awaitTermination(numberOfSeconds / 2, TimeUnit.SECONDS);
    }

    private static class Task implements Callable<Void> {

        @Override
        public Void call() {
            byte[] array = new byte[800_000 - 20]; // 800kb
            for (int seconds = 0; seconds < 2; seconds++) {
                try {
                    // emulate processing of the object taking 5 seconds
//                    System.out.println(">> Seconds passed: " + seconds);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
