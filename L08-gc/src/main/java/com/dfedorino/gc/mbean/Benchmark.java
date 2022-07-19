package com.dfedorino.gc.mbean;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
    private volatile int garbageArraySizePerIteration = 0;
    private final List<Garbage> leakingList = new ArrayList<>();

    public void runWithoutLeak() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int localGarbageArraySize = garbageArraySizePerIteration;
            Garbage[] garbageArray = new Garbage[localGarbageArraySize];
            for (int j = 0; j < localGarbageArraySize; j++) {
                garbageArray[i] = new Garbage(new char[] {'a'});
            }
            Thread.sleep(10);
        }
    }

    public void runWithPartialLeak() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int localGarbageArraySize = garbageArraySizePerIteration;
            Garbage[] garbageArray = new Garbage[localGarbageArraySize];
            for (int j = 0; j < localGarbageArraySize; j++) {
                Garbage garbage = new Garbage(new char[] {'a'});
                garbageArray[i] = garbage;
                if (i > 950 && j > 4_000_000) {
                    leakingList.add(garbage);
                }
            }
            Thread.sleep(10);
        }
    }

    public void runWithLeak() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int localGarbageArraySize = garbageArraySizePerIteration;
            Garbage[] garbageArray = new Garbage[localGarbageArraySize];
            for (int j = 0; j < localGarbageArraySize; j++) {
                Garbage garbage = new Garbage(new char[] {'a'});
                garbageArray[i] = garbage;
                if (i % 100 == 0 && j > 4_000_000) {
                    leakingList.add(garbage);
                }
            }
            Thread.sleep(10);
        }
    }

    @Override
    public int getGarbageArraySizePerIteration() {
        return garbageArraySizePerIteration;
    }

    @Override
    public void setGarbageArraySizePerIteration(int size) {
        this.garbageArraySizePerIteration = size;
    }

    public List<Garbage> getLeakingList() {
        return leakingList;
    }
}
