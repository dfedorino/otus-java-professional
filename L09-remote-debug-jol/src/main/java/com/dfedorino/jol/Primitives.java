package com.dfedorino.jol;

import org.openjdk.jol.info.ClassLayout;

public class Primitives {
    public static void main(String[] args) {
//        System.out.println(VM.current().details());
        /*
            # Running 64-bit HotSpot VM.
            # Using compressed oop with 3-bit shift.
            # Using compressed klass with 3-bit shift.
            # Objects are 8 bytes aligned.
            # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
            >> reference   4 bytes
            >> boolean     1 byte
            >> byte        1 byte
            >> short       2 bytes
            >> char        2 bytes
            >> int         4 bytes
            >> float       4 bytes
            >> long        8 bytes
            >> double      8 bytes
            # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
        */
//        System.out.println(ClassLayout.parseClass(OneBooleanField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive boolean field - 1 byte
         * alignment - 3 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         *   OFF  SZ  TYPE DESCRIPTION              VALUE
         *   0    8   (object header: mark)         N/A
         *   8    4   (object header: class)        N/A
         *   12   1   boolean OneBooleanField.b0    N/A
         *   13   3   (object alignment gap)
         *   Instance size: 16 bytes
         */
//        System.out.println(ClassLayout.parseClass(OneByteField.class).toPrintable());
        /*
        * Expected:
        * class header - 8 bytes
        * class pointer - 4 bytes
        * primitive byte field - 1 byte
        * alignment - 3 bytes
        *
        * Total: 16 bytes
        *
        * Actual:
        *   OFF  SZ   TYPE DESCRIPTION         VALUE
        *    0   8    (object header: mark)    N/A
        *    8   4    (object header: class)   N/A
        *   12   1    byte OneByteField.b0     N/A
        *   13   3    (object alignment gap)
        *   Instance size: 16 bytes
        */
//        System.out.println(ClassLayout.parseClass(OneShortField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive short field - 2 bytes
         * alignment - 2 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         *   OFF  SZ   TYPE DESCRIPTION         VALUE
         *    0   8    (object header: mark)    N/A
         *    8   4    (object header: class)   N/A
         *   12   2    short OneShortField.s0     N/A
         *   14   2    (object alignment gap)
         *   Instance size: 16 bytes
         */
//        System.out.println(ClassLayout.parseClass(OneIntField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive int field - 4 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         *   OFF  SZ   TYPE DESCRIPTION         VALUE
         *    0   8    (object header: mark)    N/A
         *    8   4    (object header: class)   N/A
         *   12   4    short OneIntField.i0     N/A
         *   Instance size: 16 bytes
         */
//        System.out.println(ClassLayout.parseClass(OneFloatField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive float field - 4 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         *   OFF  SZ   TYPE DESCRIPTION          VALUE
         *     0   8   (object header: mark)     N/A
         *     8   4   (object header: class)    N/A
         *    12   4   long OneFloatField.l0     N/A
         *   Instance size: 16 bytes
         */
//        System.out.println(ClassLayout.parseClass(OneLongField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * padding - 4 bytes
         * primitive long field - 8 bytes
         *
         * Total: 24 bytes
         *
         * Actual:
         *   OFF  SZ   TYPE DESCRIPTION               VALUE
         *     0   8   (object header: mark)     N/A
         *     8   4   (object header: class)    N/A
         *    12   4   (alignment/padding gap)
         *    16   8   long OneLongField.l0      N/A
         *   Instance size: 24 bytes
         */
//        System.out.println(ClassLayout.parseClass(OneDoubleField.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * padding - 4 bytes
         * primitive double field - 8 bytes
         *
         * Total: 24 bytes
         *
         * Actual:
         *   OFF  SZ   TYPE DESCRIPTION          VALUE
         *     0   8   (object header: mark)     N/A
         *     8   4   (object header: class)    N/A
         *    12   4   (alignment/padding gap)
         *    16   8   long OneDoubleField.d0    N/A
         *   Instance size: 24 bytes
         */

        // TODO: measure boxed types
        // TODO: measure collections of primitive types
        // TODO: measure Apache collections of primitive types
    }

    public static class OneBooleanField {
        boolean b0;
    }

    public static class OneByteField {
        byte b0;
    }

    public static class OneShortField {
        short s0;
    }

    public static class OneCharField {
        char ch0;
    }

    public static class OneIntField {
        int i0;
    }

    public static class OneFloatField {
        float f0;
    }

    public static class OneLongField {
        long l0;
    }

    public static class OneDoubleField {
        double d0;
    }
}
