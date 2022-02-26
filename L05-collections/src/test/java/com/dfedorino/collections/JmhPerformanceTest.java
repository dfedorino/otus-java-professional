package com.dfedorino.collections;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
/*
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op

    Benchmark                                       Mode  Cnt   Score   Error  Units
    JmhPerformanceTest.array_list_performance_test  avgt   25  20,621 ± 1,150  ms/op
    JmhPerformanceTest.int_array_performance_test   avgt   25   0,798 ± 0,109  ms/op
 */

//@BenchmarkMode(Mode.SingleShotTime)
/*
    # Warmup: <none>
    # Measurement: 1 iterations, single-shot each
    # Timeout: 10 min per iteration
    # Threads: 1 thread
    # Benchmark mode: Single shot invocation time

    Benchmark                                       Mode  Cnt   Score    Error  Units
    JmhPerformanceTest.array_list_performance_test    ss    5  40,834 ± 15,258  ms/op
    JmhPerformanceTest.int_array_performance_test     ss    5  14,924 ± 30,600  ms/op
 */

@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JmhPerformanceTest {
    public static final int MAX_ARRAY_SIZE = 1_000_000;

    /**
     * To get test results, run ../gradlew jmh
     */
    @Benchmark
    public void array_list_performance_test() {
        List<Integer> list = new ArrayList<>(MAX_ARRAY_SIZE);
        for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
            list.add(i, i);
        }
        for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
            list.get(i);
        }
    }

    @Benchmark
    public void int_array_performance_test() throws Exception {
        try (IntArray intArray = new IntArray(MAX_ARRAY_SIZE)) {
            for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
                intArray.setValue(i, i);
            }
            for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
                intArray.getValue(i);
            }
        }
    }
}
