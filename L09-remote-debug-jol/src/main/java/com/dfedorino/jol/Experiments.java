package com.dfedorino.jol;

import org.openjdk.jol.info.ClassLayout;

public class Experiments {
    public static void main(String[] args) {
//        Will there be alignment between fields or not?
//        System.out.println(ClassLayout.parseClass(Mixed.class).toPrintable());
        /*
        * Expected:
        * class header - 12 bytes
        * boolean - 1 byte
        * boolean - 1 byte
        * alignment - 2 bytes
        * int - 4 bytes
        * gap - 4 bytes
        *
        * Total size: 24 bytes
        *
        * Actual:
        * OFF  SZ      TYPE DESCRIPTION               VALUE
        *  0   8           (object header: mark)     N/A
        *  8   4           (object header: class)    N/A
        * 12   4       int Mixed.i                   N/A
        * 16   1   boolean Mixed.b0                  N/A
        * 17   1   boolean Mixed.b1                  N/A
        * 18   6           (object alignment gap)
        *
        * Instance size: 24 bytes
        * */

//        System.out.println(ClassLayout.parseClass(WithoutAlignment.class).toPrintable());
        /*
        * Expected:
        * class header - 12 bytes
        * boolean - 1 byte
        *
        * Total size: 13 bytes
        *
        * Actual:
        * OFF  SZ      TYPE DESCRIPTION               VALUE
        *  0   8           (object header: mark)     N/A
        *  8   4           (object header: class)    N/A
        * 12   1   boolean WithoutAlignment.b0       N/A
        * 13   3           (object alignment gap)
        *
        * Instance size: 16 bytes
        * */

        System.out.println(ClassLayout.parseClass(Child.class).toPrintable());
        /*
        * OFF  SZ      TYPE DESCRIPTION               VALUE
        *   0   8           (object header: mark)     N/A
        *   8   4           (object header: class)    N/A
        *  12   1   boolean Parent.isParent           N/A
        *  13   1   boolean Child.isParent            N/A
        *  Instance size: 16 bytes
        * */

    }

    private static class Mixed {
        boolean b0;
        int i;
        boolean b1;
    }

    private static class Parent {
        boolean isParent = true;
    }

    private static class Child extends Parent {
        boolean isParent = false;
    }
}

record WithoutAlignment(boolean b0) {}
