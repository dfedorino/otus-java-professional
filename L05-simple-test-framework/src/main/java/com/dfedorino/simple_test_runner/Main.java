package com.dfedorino.simple_test_runner;

public record Main(SimpleTestRunner runner) {
    public static void main(String[] args) {
        SimpleTestRunner runner = new SimpleTestRunner();
        String finalReport = runner.runTests(args);
        System.out.println();
        System.out.println("Test Report:");
        System.out.println(finalReport);
    }
}
