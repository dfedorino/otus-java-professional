package com.dfedorino.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

public class Ioc {

    public static <T> T createProxy(T original) {
        LoggingHandler<T> loggingHandler = new LoggingHandler<>(original);
        Class<?> originalClass = original.getClass();
        return (T) Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(), loggingHandler);
    }

    private record LoggingHandler<T>(T originalInstance) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            if (method.isAnnotationPresent(Log.class)) {
                StringBuilder logMessage = new StringBuilder();
                logMessage.append("Log: Executed method: ").append(method.getName()).append(", params: ");

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    logMessage.append(parameters[i].getName()).append("=").append(args[i]);
                    if (i != parameters.length - 1) {
                        logMessage.append(", ");
                    }
                }
                System.out.println(logMessage);
            }
            return method.invoke(originalInstance, args);
        }
    }
}
