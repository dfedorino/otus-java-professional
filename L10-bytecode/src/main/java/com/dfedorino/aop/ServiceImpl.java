package com.dfedorino.aop;

public class ServiceImpl implements Service {
    @Override
    // @Log - does not work here as only interface methods are inspected
    public void calculation(int param1) {
        this.calculation(param1, 0);
    }

    @Override
    public void calculation(int param1, int param2) {
        this.calculation(param1, param2, "Result");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println(">> " + param3 + ": " + (param1 + param2));
    }
}
