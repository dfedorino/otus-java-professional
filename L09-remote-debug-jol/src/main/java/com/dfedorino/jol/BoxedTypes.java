package com.dfedorino.jol;

import org.openjdk.jol.info.ClassLayout;

public class BoxedTypes {
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(Boolean.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive boolean - 1 byte
         * alignment - 3 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION          VALUE
         *  0   8   (object header: mark)     N/A
         *  8   4   (object header: class)    N/A
         * 12   1   boolean Boolean.value     N/A
         * 13   3   (object alignment gap)
         *
         * Instance size: 16 bytes
         * */

        System.out.println(ClassLayout.parseClass(Integer.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive int - 4 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION          VALUE
         *  0   8   (object header: mark)     N/A
         *  8   4   (object header: class)    N/A
         * 12   1   int Integer.value         N/A
         * 13   3   (object alignment gap)
         *
         * Instance size: 16 bytes
         * */

        System.out.println(ClassLayout.parseClass(Long.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * padding - 4 bytes
         * primitive long - 8 bytes
         *
         * Total: 24 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION          VALUE
         *  0   8   (object header: mark)     N/A
         *  8   4   (object header: class)    N/A
         * 12   4   (alignment/padding gap)
         * 16   1   long Long.value           N/A
         *
         * Instance size: 24 bytes
         * */

        System.out.println(ClassLayout.parseClass(Character.class).toPrintable());
        /*
         * Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * primitive char - 2 bytes
         * alignment - 2 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION          VALUE
         *  0   8   (object header: mark)     N/A
         *  8   4   (object header: class)    N/A
         * 12   2   char Character.value      N/A
         * 14   2   (object alignment gap)
         *
         * Instance size: 16 bytes
         * */

        System.out.println(ClassLayout.parseClass(String.class).toPrintable());
        /*
         * Actual:
         * OFF  SZ      TYPE DESCRIPTION               VALUE
         *  0   8           (object header: mark)     N/A
         *  8   4           (object header: class)    N/A
         * 12   4       int String.hash               N/A
         * 16   1      byte String.coder              N/A
         * 17   1   boolean String.hashIsZero         N/A
         * 18   2           (alignment/padding gap)
         * 20   4    byte[] String.value              N/A
         *
         * Instance size: 24 bytes
         * */

        String a = "a";         // String = 24 bytes, char[] = 24 bytes, total = 48 bytes, space loss = 2 + 11 = 13 bytes
        String a8 = "aaaaaaaa"; // String = 24 bytes, char[] = 24 bytes, total = 48 bytes, space loss = 2 + 4 = 6 bytes

        // TODO: measure boxed types
    }
}
