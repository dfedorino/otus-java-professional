package com.dfedorino.remote_debug;

public class RemoteApplication {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            count += 100;
            incrementCount();
            System.out.println(">> Count: " + count);
            Thread.sleep(2000);
        }
    }

    private static void incrementCount() {
        count += 1000;
    }
}
