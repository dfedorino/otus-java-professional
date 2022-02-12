package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.SimpleAfterEach;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleBeforeEach;
import com.dfedorino.simple_test_runner.simple_annotation.SimpleTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Main(SimpleTestRunner runner, TestFileVisitor fileVisitor) {
    public static void main(String[] args) throws IOException {
        List<String> testClassesNames = findAllTestClassesInCurrentDir();
        SimpleTestRunner runner = new SimpleTestRunner();
        List<Class<?>> testClasses = runner.convertToClasses(testClassesNames);
        String testReportDivider = Stream.generate(() -> "-").limit(100).collect(Collectors.joining());
        StringBuilder testClassReport = new StringBuilder(testReportDivider).append(System.lineSeparator());
        for (Class<?> testClass : testClasses) {
            List<TestMethod> testMethods = runner.scanForAnnotatedMethods(testClass, SimpleTest.class);
            List<TestMethod> beforeTestMethods = runner.scanForAnnotatedMethods(testClass, SimpleBeforeEach.class);
            List<TestMethod> afterTestMethods = runner.scanForAnnotatedMethods(testClass, SimpleAfterEach.class);
            testClassReport.append(testClass.getSimpleName()).append(System.lineSeparator());
            for (TestMethod testMethod : testMethods) {
                Object testClassInstance = runner.createTestClassInstance(testClass);
                String testReport = runner.evaluateTestMethod(testMethod, testClassInstance, beforeTestMethods, afterTestMethods);
                testClassReport.append(testReport).append(System.lineSeparator());
            }
            testClassReport.append(testReportDivider).append(System.lineSeparator());
        }
        System.out.println(testClassReport);
    }

    private static List<String> findAllTestClassesInCurrentDir() throws IOException {
        TestFileVisitor testFileVisitor = new TestFileVisitor();
        Files.walkFileTree(Path.of("."), testFileVisitor);
        return testFileVisitor.getTestFileNames();
    }
}
