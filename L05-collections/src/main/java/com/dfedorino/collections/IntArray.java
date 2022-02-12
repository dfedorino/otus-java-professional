package com.dfedorino.collections;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public class IntArray implements AutoCloseable {
    private final Unsafe unsafe;
    private long arrayBeginningIndex;
    private final long elementSizeInBytes;
    private long capacity;
    private int size;

    public IntArray(int capacity) throws Exception {
        this.capacity = capacity;
        elementSizeInBytes = Integer.SIZE / 8; // why divide by 8? why not Bytes.SIZE?
        unsafe = createUnsafeInstance();
        arrayBeginningIndex = unsafe.allocateMemory(capacity * elementSizeInBytes);
    }

    public void setValue(int index, int value) {
        if (index == capacity) {
            capacity *= 2;
            arrayBeginningIndex = unsafe.reallocateMemory(arrayBeginningIndex, capacity * elementSizeInBytes);
        }
        unsafe.putInt(calculateIndex(index), value);
        size++;
    }

    public int getValue(int index) {
        return unsafe.getInt(calculateIndex(index));
    }

    @Override
    public void close() {
        unsafe.freeMemory(arrayBeginningIndex);
    }

    private long calculateIndex(int index) {
        return arrayBeginningIndex + index * elementSizeInBytes; // why index is multiplied by element capacity?
    }

    private Unsafe createUnsafeInstance() throws Exception {
        Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
        unsafeConstructor.setAccessible(true);
        return unsafeConstructor.newInstance();
    }
}
