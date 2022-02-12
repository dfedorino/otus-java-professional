package com.dfedorino.collections;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class NaivePerformanceTest {
    @ParameterizedTest
    @MethodSource("arguments")
    void when_array_list_then_adding_million_elements_under_fifty_ms(String testCase, Runnable code, long expectedTimeInMs, int timesToRun) {
        long averageExecutionTime = 0;
        for (int i = 0; i < timesToRun; i++) {
            long start = System.currentTimeMillis();
            code.run();
            long end = System.currentTimeMillis();
            averageExecutionTime += end - start;
        }

        averageExecutionTime /= timesToRun;

        System.out.println(">> " + testCase + " average execution time -> " + averageExecutionTime + "ms");

        /*
            Without memory release:
            >> ArrayList average execution time -> 22ms
            >> IntArray average execution time -> 3ms // wtf??

            With memory release:
            >> ArrayList average execution time -> 20ms
            >> IntArray average execution time -> 1ms // WTF??
         */

        assertThat(averageExecutionTime).isLessThan(expectedTimeInMs);
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("ArrayList", arrayListTest(), 50L, 100),
                Arguments.of("IntArray", intArrayTest(), 25L, 100)
        );
    }

    private static Runnable arrayListTest() {
        return () -> {
            int maxSize = 1_000_000;
            List<Integer> list = new ArrayList<>(maxSize);
            for (int i = 0; i < maxSize; i++) {
                list.add(i, i);
            }
            for (int i = 0; i < maxSize; i++) {
                int value = list.get(i);
            }
        };
    }

    private static Runnable intArrayTest() {
        return () -> {
            int maxSize = 1_000_000;
            try(IntArray list = new IntArray(maxSize)) {
                for (int i = 0; i < maxSize; i++) {
                    list.setValue(i, i);
                }
                for (int i = 0; i < maxSize; i++) {
                    list.getValue(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
