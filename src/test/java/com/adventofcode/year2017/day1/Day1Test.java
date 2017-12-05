package com.adventofcode.year2017.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    @Test
    void samples() throws Exception {
        assertEquals(3, Day1.findSum("1122"));
        assertEquals(4, Day1.findSum("1111"));
        assertEquals(0, Day1.findSum("1234"));
        assertEquals(9, Day1.findSum("91212129"));
    }
}