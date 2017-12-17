package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    @Test
    void part1samples() throws Exception {
        assertEquals(3, Day1.findSum("1122", 1));
        assertEquals(4, Day1.findSum("1111", 1));
        assertEquals(0, Day1.findSum("1234", 1));
        assertEquals(9, Day1.findSum("91212129", 1));
    }

    @Test
    void part2samples() throws Exception {
        assertEquals(6, Day1.findSum("1212", 2));
        assertEquals(0, Day1.findSum("1221", 2));
        assertEquals(4, Day1.findSum("123425", "123425".length()/2));
        assertEquals(12, Day1.findSum("123123", "123123".length()/2));
        assertEquals(4, Day1.findSum("12131415", "12131415".length()/2));
    }
}