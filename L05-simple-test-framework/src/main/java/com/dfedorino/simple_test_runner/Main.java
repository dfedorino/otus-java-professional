package com.dfedorino.simple_test_runner;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public record Main(SimpleTestRunner runner) {
    public static void main(String[] args) {
        SimpleTestRunner runner = new SimpleTestRunner();
        String finalReport = runner.convertToClasses(asList(args)).stream()
                .map(runner::evaluateTestClass)
                .collect(Collectors.joining());
        System.out.println(finalReport);
    }
}
