package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String... args) throws Exception {

        List<Integer> instructions = Files.lines(Paths.get(Day5.class.getResource("/inputs/day5.txt").toURI()))
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        System.out.println("Part 1:");
        System.out.println(stepsToReachExit(instructions, (o) -> o + 1));

        System.out.println("Part 2:");
        System.out.println(stepsToReachExit(instructions, (o) -> {
            if (o > 2) {
                return o - 1;
            }
            return o + 1;
        }));
    }

    static int stepsToReachExit(List<Integer> instructions, Function<Integer, Integer> offsetSupplier) {
        List<Integer> offsets = new ArrayList<>(instructions);

        for (int steps = 0, i = 0; ; ) {
            Integer offset = offsets.get(i);
            offsets.set(i, offsetSupplier.apply(offset));

            i += offset;
            steps++;

            if (i < 0 || i >= instructions.size()) {
                return steps;
            }

        }
    }
}
