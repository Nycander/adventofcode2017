package com.adventofcode.year2017;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

interface SpinlockContract {
    Spinlock createSpinlock();

    @Test
    default void empty() throws Exception {
        Spinlock empty = createSpinlock();

        Assertions.assertEquals(0, empty.getValue());
        Assertions.assertArrayEquals(new int[]{0}, empty.getBuffer());
    }

    @Test
    default void sample() throws Exception {
        Spinlock stepped = createSpinlock().withStep(3);
        Assertions.assertArrayEquals(new int[]{0, 1}, stepped.getBuffer());
        Assertions.assertEquals(1, stepped.getValue());
    }

    @Test
    default void sampleStep2() throws Exception {
        Spinlock stepped = ImmutableSpinlock.create(1, new int[]{0, 1}).withStep(3);
        Assertions.assertArrayEquals(new int[]{0, 2, 1}, stepped.getBuffer());
        Assertions.assertEquals(2, stepped.getValue());
    }

    @Test
    default void sampleStep3() throws Exception {
        Spinlock stepped = ImmutableSpinlock.create(1, new int[]{0, 2, 1}).withStep(3);
        Assertions.assertArrayEquals(new int[]{0, 2, 3, 1}, stepped.getBuffer());
        Assertions.assertEquals(3, stepped.getValue());
    }


    @Test
    default void sampleStep4() throws Exception {
        Spinlock stepped = createSpinlock()
                .withStep(3)
                .withStep(3)
                .withStep(3)
                .withStep(3);

        Assertions.assertArrayEquals(new int[]{0, 2, 4, 3, 1}, stepped.getBuffer());
        Assertions.assertEquals(4, stepped.getValue());
    }


    @Test
    default void sampleStep5() throws Exception {
        Spinlock stepped = createSpinlock()
                .withStep(3)
                .withStep(3)
                .withStep(3)
                .withStep(3)
                .withStep(3);

        Assertions.assertArrayEquals(new int[]{0, 5, 2, 4, 3, 1}, stepped.getBuffer());
        Assertions.assertEquals(5, stepped.getValue());
    }

    @Test
    default void sampleRun() throws Exception {
        Spinlock complete = createSpinlock().runSteps(2017, 3);

        Assertions.assertEquals(2017, complete.getValue());
        Assertions.assertEquals(638, complete.nextValue());
    }
}

class SpinlockTest implements SpinlockContract {
    @Override
    public Spinlock createSpinlock() {
        return ImmutableSpinlock.empty();
    }
}

class MutableSpinlockTest implements SpinlockContract {
    @Override
    public Spinlock createSpinlock() {
        return MutableSpinlock.empty(2018);
    }
}

