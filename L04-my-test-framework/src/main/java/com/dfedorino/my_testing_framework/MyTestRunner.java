package com.dfedorino.my_testing_framework;

import com.dfedorino.my_testing_framework.annotations.MyAfterEach;
import com.dfedorino.my_testing_framework.annotations.MyBeforeEach;
import com.dfedorino.my_testing_framework.annotations.MyTest;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.*;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

public class MyTestRunner extends ParentRunner<FrameworkMethod> {

    public MyTestRunner(Class testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected void runChild(FrameworkMethod testMethod, RunNotifier notifier) {
        Description description = describeChild(testMethod);
        Statement statement = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                methodBlock(testMethod).evaluate();
            }
        };
        runLeaf(statement, description, notifier);
    }

    private Statement methodBlock(FrameworkMethod testMethod) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return getTestClass().getJavaClass().getDeclaredConstructor().newInstance();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = new InvokeMethod(testMethod, test);
        statement = possiblyExpectingExceptions(testMethod, statement);
        statement = withBefores(test, statement);
        statement = withAfters(test, statement);
        statement = withInterruptIsolation(statement);
        return statement;
    }

    protected Statement possiblyExpectingExceptions(FrameworkMethod testMethod, Statement next) {
        MyTest annotation = testMethod.getAnnotation(MyTest.class);
        Class<? extends Throwable> expectedExceptionClass = getExpectedException(annotation);
        return expectedExceptionClass != null ? new ExpectException(next, expectedExceptionClass) : next;
    }

    private Class<? extends Throwable> getExpectedException(MyTest annotation) {
        if (annotation == null || annotation.expected() == MyTest.None.class) {
            return null;
        } else {
            return annotation.expected();
        }
    }

    protected Statement withBefores(Object target, Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(MyBeforeEach.class);
        return befores.isEmpty() ? statement : new RunBefores(statement, befores, target);
    }

    protected Statement withAfters(Object target, Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(MyAfterEach.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
    }


    @Override
    protected List<FrameworkMethod> getChildren() {
        return getTestClass().getAnnotatedMethods(MyTest.class);
    }

    @Override
    protected Description describeChild(FrameworkMethod testMethod) {
        return Description.createTestDescription(getTestClass().getJavaClass(), testMethod.getName(), testMethod.getAnnotations());
    }
}
