package com.dfedorino.simple_test_runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTestRunner {
    public String evaluateTestMethod(TestMethod testMethod, Object testClassInstance, List<TestMethod> befores, List<TestMethod> afters) {
        StringBuilder report = new StringBuilder();
        try {
            for (TestMethod before : befores) {
                before.evaluate(testClassInstance);
            }
            testMethod.evaluate(testClassInstance);
            report.append("... PASSED ...")
                    .append(System.lineSeparator())
                    .append(String.format("Test %s with hash %s passed", testMethod.getTestMethodName(), testClassInstance.hashCode()));
        } catch (Throwable throwable) {
            report.append("!!! FAILED !!!")
                    .append(System.lineSeparator())
                    .append(String.format("Test %s with hash %s failed:", testMethod.getTestMethodName(), testClassInstance.hashCode()))
                    .append(System.lineSeparator())
                    .append(String.format("Exception: %s", throwable.getClass().getSimpleName()))
                    .append(System.lineSeparator())
                    .append(String.format("Reason: %s", throwable.getMessage()));
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

    public List<Class<?>> convertToClasses(List<String> classNames) {
        return classNames.stream().map(this::wrappedForName).collect(Collectors.toList());
    }

    public Class<?> wrappedForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
