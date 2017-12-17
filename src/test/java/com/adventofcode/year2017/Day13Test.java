package com.adventofcode.year2017;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day13Test {

    @Test
    void testPosition() {
        Day13.Scanner scanner = new Day13.Scanner(1, 2);

        int pos = scanner.positionAtPicoSecond(2);

        Assertions.assertEquals(0, pos);
        System.out.println(scanner.humanToString(2));
    }
}