package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.SimpleTest;

import static com.dfedorino.simple_test_runner.SimpleAssertions.assertTheyAreEqual;

public class SimpleTestExample4 {
    @SimpleTest
    public void sync_successful_test_1() {
        System.out.println(">> Sync test 1 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void sync_successful_test_2() {
        System.out.println(">> Sync test 2 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void sync_successful_test_3() {
        System.out.println(">> Sync test 3 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void sync_successful_test_4() {
        System.out.println(">> Sync test 4 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }

    @SimpleTest
    public void sync_successful_test_5() {
        System.out.println(">> Sync test 5 in test class instance with hash -> " + this.hashCode());
        assertTheyAreEqual(2, 2);
    }
}
