package com.adventofcode.year2017;

public class Day15 {
    public static void main(String... args) {
        System.out.println("Part 1");
        Generator generatorA = new Generator(16807, 4, 591);
        Generator generatorB = new Generator(48271, 8, 393);
        Judge part1Judge = new Judge();
        for (int i = 0; i < 40_000_000; i++) {
            part1Judge.compare(generatorA.generateValue(), generatorB.generateValue());
        }
        System.out.println(part1Judge.matches);

        System.out.println("Part 2");
        generatorA = new Generator(16807, 4, 591);
        generatorB = new Generator(48271, 8, 393);
        Judge part2Judge = new Judge();
        for (int i = 0; i < 5_000_000; i++) {
            part2Judge.compare(generatorA.generateAcceptableValue(), generatorB.generateAcceptableValue());
        }
        System.out.println(part2Judge.matches);
    }


    static class Generator {
        private final long factor;
        private final long acceptableDivider;
        private long previousValue;

        Generator(long factor, long acceptableDivider, long startingValue) {
            this.factor = factor;
            this.acceptableDivider = acceptableDivider;
            this.previousValue = startingValue;
        }

        long generateValue() {
            return previousValue = (previousValue * factor) % 2147483647;
        }

        long generateAcceptableValue() {
            while (true) {
                long value = generateValue();
                if (value % acceptableDivider == 0) {
                    return value;
                }
            }
        }
    }

    static class Judge {
        private int matches = 0;

        void compare(long a, long b) {
            matches += (a & 0b1111111111111111) == (b & 0b1111111111111111) ? 1 : 0;
        }
    }
}
