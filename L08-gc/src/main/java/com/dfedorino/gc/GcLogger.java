package com.dfedorino.gc;

import com.dfedorino.gc.mbean.Benchmark;
import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcLogger {
    private static final Map<String, Long> gcTotals = new HashMap<>();

    public static void main(String[] args) throws Exception {
        System.out.println(">> Started at pid: " + ManagementFactory.getRuntimeMXBean().getPid());
        trackGcEvents();

        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("com.dfedorino:type=Benchmark");

        int garbageArraySize = 5_000_000;
        Benchmark benchmark = new Benchmark();
        platformMBeanServer.registerMBean(benchmark, objectName);
        benchmark.setGarbageArraySizePerIteration(garbageArraySize);

        long start = System.currentTimeMillis();
//        benchmark.runWithoutLeak();
//        benchmark.runWithPartialLeak();
        benchmark.runWithLeak();
        long end = System.currentTimeMillis();

        System.out.println(">> Benchmark running time: " + (end - start) / 1000.0 + "s");
        System.out.println(">> GC totals:");
        gcTotals.entrySet().forEach(entry -> System.out.println(">> " + entry.getKey() + ": " + entry.getValue()));
        System.out.println(">> Leaked objects: " + benchmark.getLeakingList().size());

        /*
         * Benchmark description (without leak):
         * 1. Create an array of Garbage (String-like) objects of 5_000_000 elements;
         * 2. Initiate every object with a 'new char[] {'a'}' element;
         * 3. Sleep for 10ms;
         * 4. Repeat 1000 times;
         *
         * VM Options:
         * -Xms512m
         * -Xmx512m
         * -XX:+HeapDumpOnOutOfMemoryError
         * -----------------------------------------------------------------------------------------------------------------
         * G1 (default):
         * Total Application Running Time: 77.757s
         * Total Garbage Collection Running Time: 932ms
         * Useful Time: 99%
         *
         * Minor Garbage Collections: 753
         * G1 Humongous Allocation: 243
         * G1 Evacuation Pause: 510
         * -----------------------------------------------------------------------------------------------------------------
         * Serial GC:
         * -XX:+UseSerialGC
         * Total Application Running Time: 54.654s
         * Total Garbage Collection Running Time: 15494ms = ~15.5s
         * Useful Time: 72%
         *
         * Minor Garbage Collections: 1545
         * Major Garbage Collections: 64
         * -----------------------------------------------------------------------------------------------------------------
         * Parallel GC:
         * -XX:+UseParallelGC
         * Total Application Running Time: 68.61s
         * Total Garbage Collection Running Time: 27487ms = ~27.5s
         * Useful Time: 60%
         *
         * Minor Garbage Collections: 2162
         * Major Garbage Collections: 82
         *
         * Allocation Failure: 2162
         * Ergonomics: 82
         * -----------------------------------------------------------------------------------------------------------------
         * Z Garbage Collector:
         * -XX:+UseZGC
         * Total Application Running Time: 61.297s
         * Total Garbage Collection Running Time: 19307ms = ~19.3s
         * Useful Time: 69%
         *
         * Warmup: 12
         * end of GC pause: 1923
         * end of GC cycle: 641
         * Allocation Rate: 256
         *  */


        /*
         * Benchmark description (with partial leak):
         * 1. Create an array of Garbage (String-like) objects of 5_000_000 elements;
         * 2. Initiate every object with a 'new char[] {'a'}' element;
         * 3. Add half of the elements into the leaking list;
         * 4. Sleep for 10ms;
         * 5. Repeat 1000 times;
         *
         * VM Options:
         * -Xms256m
         * -Xmx256m
         * -XX:+HeapDumpOnOutOfMemoryError
         * -----------------------------------------------------------------------------------------------------------------
         * G1 (default):
         * Total Application Running Time: 145.212s
         * Total Garbage Collection Running Time: 8589ms ~8.6s
         * Useful Time: 95%
         *
         * Minor Garbage Collections: 1519
         * G1 Humongous Allocation: 359
         * G1 Evacuation Pause: 1160
         * -----------------------------------------------------------------------------------------------------------------
         * Serial GC:
         * -XX:+UseSerialGC
         * Total Application Running Time: 145.408s
         * Total Garbage Collection Running Time: 53393ms = ~53.4s
         * Useful Time: 64%
         *
         * Minor Garbage Collections: 3100
         * Major Garbage Collections: 167
         * -----------------------------------------------------------------------------------------------------------------
         * Parallel GC:
         * -XX:+UseParallelGC
         * Total Application Running Time: 163.34s
         * Total Garbage Collection Running Time: 67425ms = ~67.4s
         * Useful Time: 59%
         *
         * Minor Garbage Collections: 2503
         * Major Garbage Collections: 167
         *
         * Allocation Failure: 2503
         * Ergonomics: 167
         * -----------------------------------------------------------------------------------------------------------------
         * Z Garbage Collector:
         * -XX:+UseZGC
         * Total Application Running Time: 217.187s
         * Total Garbage Collection Running Time: 123888ms = ~123.9s
         * Useful Time: 43%
         *
         * Warmup: 12
         * end of GC pause: 6121
         * end of GC cycle: 2040
         * Allocation Rate: 3384
         * Allocation Stall: 4765
         *  */

        /*
         * Benchmark description (with leak):
         * 1. Create an array of Garbage (String-like) objects of 5_000_000 elements;
         * 2. Initiate every object with a 'new char[] {'a'}' element;
         * 3. Add 1M objects to the leaking list at every 100th iteration, 10M objects in total;
         * 4. Sleep for 10ms;
         * 5. Repeat 1000 times;
         *
         * VM Options:
         * -Xms512m
         * -Xmx512m
         * -XX:+HeapDumpOnOutOfMemoryError
         * -----------------------------------------------------------------------------------------------------------------
         * G1 (default):
         * Total Application Running Time: 2 min 34 sec
         * OutOfMemoryError
         * -----------------------------------------------------------------------------------------------------------------
         * Serial GC:
         * -XX:+UseSerialGC
         * Total Application Running Time: 145.408s
         * Total Garbage Collection Running Time: 53393ms = ~53.4s
         * Useful Time: 64%
         *
         * Minor Garbage Collections: 3100
         * Major Garbage Collections: 167
         * -----------------------------------------------------------------------------------------------------------------
         * Parallel GC:
         * -XX:+UseParallelGC
         * Total Application Running Time: 163.34s
         * Total Garbage Collection Running Time: 67425ms = ~67.4s
         * Useful Time: 59%
         *
         * Minor Garbage Collections: 2503
         * Major Garbage Collections: 167
         *
         * Allocation Failure: 2503
         * Ergonomics: 167
         * -----------------------------------------------------------------------------------------------------------------
         * Z Garbage Collector:
         * -XX:+UseZGC
         * Total Application Running Time: 217.187s
         * Total Garbage Collection Running Time: 123888ms = ~123.9s
         * Useful Time: 43%
         *
         * Warmup: 12
         * end of GC pause: 6121
         * end of GC cycle: 2040
         * Allocation Rate: 3384
         * Allocation Stall: 4765
         *  */

    }

    private static void trackGcEvents() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeans) {
            System.out.println(">> GC bean name: " + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    String gcName = gcInfo.getGcName();
                    String gcAction = gcInfo.getGcAction();
                    String gcCause = gcInfo.getGcCause();
                    long duration = gcInfo.getGcInfo().getDuration();

                    gcTotals.put(gcAction, gcTotals.getOrDefault(gcAction, 0L) + 1);
                    gcTotals.put(gcCause, gcTotals.getOrDefault(gcCause, 0L) + 1);
                    gcTotals.put("Total Duration", gcTotals.getOrDefault("Total Duration", 0L) + duration);

                    System.out.println(">> Notification from: " + gcName + ", action: " + gcAction + ", cause: " + gcCause + ", duration: " + duration + "ms");

                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
