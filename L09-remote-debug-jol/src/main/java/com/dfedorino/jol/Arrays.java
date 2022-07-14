package com.dfedorino.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.BitSet;

public class Arrays {
    public static void main(String[] args) {
//        System.out.println(ClassLayout.parseClass(FiveBooleanFields.class).toPrintable());
        /*
        * Expected:
        * class header - 8 bytes
        * class pointer - 4 bytes
        * primitive boolean fields - 5 bytes
        * alignment - 7 bytes
        *
        * Total 24 bytes
        *
        * Actual:
        * OFF  SZ  TYPE DESCRIPTION                          VALUE
        * 0    8   (object header: mark)                     N/A
        * 8    4   (object header: class)                    N/A
        * 12   1   boolean FiveBooleanFields.b0              N/A
        * 13   1   boolean FiveBooleanFields.b1              N/A
        * 14   1   boolean FiveBooleanFields.b2              N/A
        * 15   1   boolean FiveBooleanFields.b3              N/A
        * 16   1   boolean FiveBooleanFields.b4              N/A
        * 17   7   (object alignment gap)
        * Instance size: 24 bytes
        * */
//        System.out.println(ClassLayout.parseClass(BooleanArrayField.class).toPrintable());
//        System.out.println(ClassLayout.parseClass(boolean[].class).toPrintable());
        /*
         * BooleanArrayField.class Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * boolean array reference - 4 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ   TYPE DESCRIPTION              VALUE
         * 0    8   (object header: mark)           N/A
         * 8    4   (object header: class)          N/A
         * 12   4   boolean[] BooleanArrayField.ba  N/A
         * Instance size: 16 bytes
         *
         * Boolean array class Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * >> array length - 4 bytes
         * >> padding gap - 4 bytes
         * >> boolean [Z - 0 bytes
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION       VALUE
         *  0   8   (object header: mark)   N/A
         *  8   4   (object header: class)  N/A
         * 12   4   (array length)          N/A
         * 12   4   (alignment/padding gap)
         * 16   0   boolean [Z.<elements>   N/A
         * Instance size: 16 bytes
         *
         * Total: 40 bytes
         * >> Total: 32 bytes
         * */
        BooleanArrayField booleanArrayField = new BooleanArrayField();
//        System.out.println(ClassLayout.parseInstance(booleanArrayField).toPrintable());
        System.out.println(ClassLayout.parseInstance(booleanArrayField.ba).toPrintable());
        /*
         * BooleanArrayField instance Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * boolean array reference - 4 bytes
         *
         * Total: 16 bytes
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION                VALUE
         *  0   8   (object header: mark)           0x0000000000000001 (non-biasable; age: 0)
         *  8   4   (object header: class)          0x00c01a00
         * 12   4   boolean[] BooleanArrayField.ba  [false, false, false, false, false]
         * Instance size: 16 bytes
         *
         * Boolean array instance Expected:
         * class header - 8 bytes
         * class pointer - 4 bytes
         * array length - 4 bytes
         * >> padding gap - 4 bytes
         * boolean [Z - 5 bytes
         * alignment - 3 bytes
         * Total: 24 bytes >> why 24 and not 28 ???
         *
         * Actual:
         * OFF  SZ  TYPE DESCRIPTION          VALUE
         *  0   8   (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         *  8   4   (object header: class)    0x00000960
         * 12   4   (array length)            5
         * 12   4   (alignment/padding gap)
         * 16   5   boolean [Z.<elements>     N/A
         * 21   3   (object alignment gap)
         * Instance size: 24 bytes
         *
         * Total: 40 bytes
         * */
        System.out.println(GraphLayout.parseInstance(new BitSet(1024)).toPrintable());
        /*
        * OFF  SZ      TYPE DESCRIPTION              VALUE
        *  0   8           (object header: mark)     N/A
        *  8   4           (object header: class)    N/A
        * 12   4       int BitSet.wordsInUse         N/A
        * 16   1   boolean BitSet.sizeIsSticky       N/A
        * 17   3           (alignment/padding gap)
        * 20   4    long[] BitSet.words              N/A
        * Instance size: 24 bytes
        * */

//        boolean[] ba = new boolean[9];
//        BitSet bs = new BitSet(9);
//        bs.flip(0, 9);

//        System.out.println(GraphLayout.parseInstance(ba).toPrintable()); // expected size = 32 bytes
//        System.out.println(GraphLayout.parseInstance(bs).toPrintable()); // expected size = 24 bytes
    }

    public static class FiveBooleanFields {
        boolean b0;
        boolean b1;
        boolean b2;
        boolean b3;
        boolean b4;
    }

    public static class BooleanArrayField {
        boolean[] ba = new boolean[1];
    }
}
