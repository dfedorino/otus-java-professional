package com.dfedorino.gc.mbean;

public interface BenchmarkMBean {
    int getGarbageArraySizePerIteration();

    void setGarbageArraySizePerIteration(int size);
}
