package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleTestRunner {
    private final String testReportDivider = Stream.generate(() -> "-").limit(100).collect(Collectors.joining());

    public String runTests(String[] args) {
        return Arrays.stream(args)
                .map(this::wrappedForName)
                .map(this::evaluateTestClass)
                .collect(Collectors.joining());
    }

    public String evaluateTestClass(Class<?> testClass) {
        List<TestMethod> testMethods = scanForAnnotatedMethods(testClass, SimpleTest.class);
        List<TestMethod> befores = scanForAnnotatedMethods(testClass, SimpleBeforeEach.class);
        List<TestMethod> afters = scanForAnnotatedMethods(testClass, SimpleAfterEach.class);
        Stream<TestMethod> evaluationProcess = testMethods.stream();
        if (testClass.isAnnotationPresent(ParallelExecution.class)) {
            evaluationProcess =  evaluationProcess.parallel();
        }
        return evaluationProcess
                .map(test -> evaluateTestMethod(testClass, befores, test, afters))
                .collect(Collectors.joining(testReportDivider + System.lineSeparator(), testReportDivider + System.lineSeparator(), ""));
    }

    public String evaluateTestMethod(Class<?> testClass, List<TestMethod> befores, TestMethod testMethod, List<TestMethod> afters) {
        Object testClassInstance = createTestClassInstance(testClass);
        StringBuilder report = new StringBuilder();
        try {
            for (TestMethod before : befores) {
                before.evaluate(testClassInstance);
            }
            testMethod.evaluate(testClassInstance);
            appendPassedMessage(report, testMethod, testClassInstance);
        } catch (Throwable thrown) {
            if (testMethod.method().isAnnotationPresent(ExpectToThrow.class)) {
                Class<?> expectedExceptionClass = testMethod.method().getAnnotation(ExpectToThrow.class).expected();
                if (thrown.getClass().getSimpleName().equals(expectedExceptionClass.getSimpleName())) {
                    appendPassedMessage(report, testMethod, testClassInstance);
                } else {
                    appendFailedMessage(report, testMethod, testClassInstance, thrown);
                }
            } else {
                appendFailedMessage(report, testMethod, testClassInstance, thrown);
            }
        } finally {
            for (TestMethod after : afters) {
                try {
                    after.evaluate(testClassInstance);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return report.toString();
    }

    private void appendPassedMessage(StringBuilder report, TestMethod testMethod, Object testClassInstance) {
        report.append("... PASSED ...")
                .append(System.lineSeparator())
                .append(String.format("Test %s with hash %s passed", testMethod.getTestMethodName(), testClassInstance.hashCode()))
                .append(System.lineSeparator());
    }

    private void appendFailedMessage(StringBuilder report, TestMethod testMethod, Object testClassInstance, Throwable throwable) {
        report.append("!!! FAILED !!!")
                .append(System.lineSeparator())
                .append(String.format("Test %s with hash %s failed:", testMethod.getTestMethodName(), testClassInstance.hashCode()))
                .append(System.lineSeparator())
                .append(String.format("Exception: %s", throwable.getClass().getSimpleName()))
                .append(System.lineSeparator())
                .append(String.format("Reason: %s", throwable.getMessage()))
                .append(System.lineSeparator());
    }

    public Object createTestClassInstance(Class<?> testClass) {
        try {
            return testClass.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TestMethod> scanForAnnotatedMethods(Class<?> testClass, Class<? extends  Annotation> annotationClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .map(TestMethod::new)
                .collect(Collectors.toList());
    }

    private Class<?> wrappedForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
