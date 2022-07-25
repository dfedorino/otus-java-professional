package com.dfedorino.aop;

public interface Service {
    @Log
    void calculation(int param1);
    @Log
    void calculation(int param1, int param2);
    @Log
    void calculation(int param1, int param2, String param3);
}
