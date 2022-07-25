package com.dfedorino.aop;

public class Main {
    public static void main(String[] args) {
        // To print out original parameters' names compile with javac -parameters
        // In Intellij:
        // Settings > Build, Execution, Deployment > Compiler > Java Compiler > Additional command line parameters

        Service service = Ioc.createProxy(new ServiceImpl());
        service.calculation(2);
        service.calculation(2, 2);
        service.calculation(2, 2, "Two plus two is");
    }
}
