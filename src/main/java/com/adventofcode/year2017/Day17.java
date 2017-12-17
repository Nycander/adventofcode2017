package com.adventofcode.year2017;

import java.util.Arrays;

public class Day17 {
    public static void main(String... args) throws Exception {

        System.out.println("Part 1:");
        System.out.println(ImmutableSpinlock.empty().runSteps(2017, 348).nextValue());


        System.out.println("Part 2:");
        ValueAfterSpinlock part2 = new ValueAfterSpinlock();
        part2.runSteps(50_000_000, 348);
        System.out.println(part2.valueAfter());
    }
}

interface Spinlock {
    Spinlock withStep(int step);
    Spinlock runSteps(int times, int steps);

    int getPosition();
    int[] getBuffer();
    int getValue();
    int nextValue();
    int valueAfter(int i);
}


class ValueAfterSpinlock {
    private int valueAfter0 = 0;

    private int currentValue = 0;
    private int currentPosition = 0;
    private int size = 1;

    public int getPosition() {
        return currentPosition;
    }

    public ValueAfterSpinlock runSteps(int times, int step) {
        for (int i = 0; i < times; i++) {
            withStep(step);
        }
        return this;
    }

    public ValueAfterSpinlock withStep(int step) {
        int pos = (currentPosition + step) % size;

        currentValue++;
        size++;
        currentPosition = pos + 1;

        if (pos == 0) {
            valueAfter0 = currentValue;
        }

        return this;
    }

    public int valueAfter() {
        return valueAfter0;
    }
}

class MutableSpinlock implements Spinlock {
    private int currentPosition;
    private int currentSize;
    private final int[] buffer;

    public MutableSpinlock(int currentPosition, int currentSize, int maxSize) {
        this.currentPosition = currentPosition;
        this.currentSize = currentSize;
        this.buffer = new int[maxSize];
    }

    public int getPosition() {
        return currentPosition;
    }

    public int[] getBuffer() {
        return Arrays.copyOf(buffer, currentSize);
    }

    private int cl(int i, int size) {
        return i % size;
    }

    public int getValue() {
        return buffer[currentPosition];
    }

    public int nextValue() {
        return buffer[cl(currentPosition + 1, currentSize)];
    }

    public int valueAfter(int i) {
        for (int j = 0; j < buffer.length; j++) {
            if (buffer[j] == i) {
                return buffer[cl(j + 1, currentSize)];
            }
        }
        throw new IllegalArgumentException();
    }

    public MutableSpinlock runSteps(int times, int step) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < times; i++) {
            this.withStep(step);
            if (i % 1_00_000 == 0) {
                System.out.println(i + " / " + times +" ("+ (int)((double)i / (double)(System.currentTimeMillis() - start)) + " i/ms )");
                start = System.currentTimeMillis();
            }
        }
        return this;
    }

    public MutableSpinlock withStep(int step) {
        int pos = cl(currentPosition + step, currentSize);
        int newPos = pos + 1;
        int newValue = getValue() + 1;

        if (newPos < currentSize) {
            System.arraycopy(this.buffer, newPos, buffer, newPos + 1, currentSize - (newPos));
        }
        buffer[cl(newPos, currentSize + 1)] = newValue;

        currentSize++;
        currentPosition = newPos;
        return this;
    }

    public static MutableSpinlock empty(int maxSize) {
        return new MutableSpinlock(0, 1, maxSize);
    }
}

class ImmutableSpinlock implements Spinlock {
    private final int currentPosition;
    private final int[] circularBuffer;

    ImmutableSpinlock(int currentPosition, int[] circularBuffer) {
        this.currentPosition = currentPosition;
        this.circularBuffer = Arrays.copyOf(circularBuffer, circularBuffer.length);
    }

    public static Spinlock empty() {
        return new ImmutableSpinlock(0, new int[]{0});
    }

    public static Spinlock create(int position, int[] buffer) {
        return new ImmutableSpinlock(position, buffer);
    }

    @Override
    public int getPosition() {
        return currentPosition;
    }

    @Override
    public int[] getBuffer() {
        return Arrays.copyOf(circularBuffer, circularBuffer.length);
    }

    @Override
    public Spinlock withStep(int step) {
        int pos = cl(currentPosition + step, circularBuffer);

        int[] newBuffer = new int[circularBuffer.length + 1];
        System.arraycopy(this.circularBuffer, 0, newBuffer, 0, pos + 1);
        newBuffer[cl(pos + 1, newBuffer)] = getValue() + 1;
        if (pos + 1 < circularBuffer.length) {
            System.arraycopy(this.circularBuffer, pos + 1, newBuffer, pos + 2, circularBuffer.length - (pos + 1));
        }

        return new ImmutableSpinlock(pos + 1, newBuffer);
    }

    private int cl(int i, int[] array) {
        return i % array.length;
    }

    @Override
    public int getValue() {
        return circularBuffer[currentPosition];
    }

    @Override
    public int nextValue() {
        return circularBuffer[cl(currentPosition + 1, circularBuffer)];
    }

    @Override
    public int valueAfter(int i) {
        for (int j = 0; j < circularBuffer.length; j++) {
            if (circularBuffer[j] == i) {
                return circularBuffer[cl(j + 1, circularBuffer)];
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Spinlock runSteps(int times, int steps) {
        Spinlock tmp = this;
        for (int i = 0; i < times; i++) {
            tmp = tmp.withStep(steps);
        }
        return tmp;
    }
}

