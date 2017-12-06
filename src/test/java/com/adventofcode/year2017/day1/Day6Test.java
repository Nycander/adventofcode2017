package com.adventofcode.year2017.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Day6Test {
	@Test
	void samplePartOne() {
		assertEquals(5, Day6.countRedistributionCycles(Arrays.asList(0, 2, 7, 0)));
	}
}