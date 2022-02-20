package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.ParallelExecution;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleAfterEach;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleBeforeEach;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleTest;

import static com.dfedorino.simple_test_runner.SimpleAssertions.assertTheyAreEqual;

@ParallelExecution
public class SimpleTestExample3 {
    @SimpleTest
    public void async_successful_test_1() {
        System.out.println(">> Async test 1 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void async_successful_test_2() {
        System.out.println(">> Async test 2 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void async_successful_test_3() {
        System.out.println(">> Async test 3 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void async_successful_test_4() {
        System.out.println(">> Async test 4 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void async_successful_test_5() {
        System.out.println(">> Async test 5 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }
}
