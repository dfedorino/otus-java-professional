package com.dfedorino.gc;

class GCPractice {
    // >> Pay attention to the JVM version and default gc

    /**
     * Task:    understand, which size allows an object
     *          to be transferred directly to old gen.
     *
     * Process: an array of given size is created on each iteration and immediately
     *          discarded
     *
     * Env:     OpenJDK 1.8 -Xmx1024m -Xms1024m -verbose:gc, CMS GC
     *          Young Gen=567m
     *              Eden Space=340m
     *              S0=113.5m
     *              S1=113.5m
     *          Old Gen=683m
     *
     *
     * Object size:
     * 8kb - 0 GC events in 100 seconds, 0 objects in old gen
     * 80kb - 0 GC events in 100 seconds, 0 objects in old gen
     * 800kb - 0 GC events in 100 seconds, 0 objects in old gen
     *
     * 1mb - 0 GC events in 100 seconds, 0 objects in old gen
     * 10mb - 4 minor GCs in 100 seconds, 0 objects in old gen
     * 100mb - 35 minor GC in 100 seconds, 0 objects in old gen
     * 170mb(half-eden) - 50 minor GCs in 100 seconds, before 1st minor GC, old gen was filled in
     * 340m (eden) - 8 minor GCs, 35 major GCs. Every minor GC had a full GC afterwards.
     *               If eden space is constantly occupied with large objects, other
     *               objects are placed into old gen immediately causing more STW pauses
     * */


    public static void main(String[] args) throws InterruptedException {
        long seconds = 0;
        while (seconds != 100) {
            boolean[] array = new boolean[340_000_000 - 20]; // 340mb
            System.out.println(">> Passed seconds: " + seconds);
            Thread.sleep(1000);
            seconds++;
        }
    }
}
