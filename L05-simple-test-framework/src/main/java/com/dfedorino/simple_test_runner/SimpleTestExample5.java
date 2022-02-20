package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.ExpectToThrow;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleTest;

public class SimpleTestExample5 {
    @SimpleTest
    @ExpectToThrow(expected = ArithmeticException.class)
    void when_expected_exception_then_passed() {
        throw new ArithmeticException("Expected exception");
    }

    @SimpleTest
    @ExpectToThrow(expected = ArithmeticException.class)
    void when_unexpected_exception_then_failed() {
        throw new IllegalArgumentException("Unexpected exception");
    }
}
