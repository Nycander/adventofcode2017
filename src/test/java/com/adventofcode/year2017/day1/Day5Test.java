package com.adventofcode.year2017.day1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    @Test
    void sampleDay1() {
        assertEquals(5, Day5.stepsToReachExit(Arrays.asList(0, 3, 0, 1, -3), (o) -> o + 1));
    }
}