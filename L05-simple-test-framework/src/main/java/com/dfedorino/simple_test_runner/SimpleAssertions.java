package com.dfedorino.simple_test_runner;

public class SimpleAssertions {
    public static void assertTheyAreEqual(int a, int b) {
        if (a != b) {
            throw new AssertionError("Expected " + a + " to be equal to " + b + ", but something went wrong");
        }
    }
}
