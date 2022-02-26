package com.dfedorino.simple_test_runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record TestMethod(Method method) {

    public Object evaluate(Object instance) throws Throwable {
        try {
            return method.invoke(instance);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    public String getTestMethodName() {
        return method.getName();
    }
}
