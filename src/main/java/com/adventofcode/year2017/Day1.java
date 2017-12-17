package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class Day1 {
    public static void main(String... args) throws Exception {
        System.out.println("Part 1:");
        Files.lines(Paths.get(Day1.class.getResource("/inputs/day1.txt").toURI()))
                .mapToInt(ints -> findSum(ints, 1))
                .forEach(System.out::println);

        System.out.println("Part 2:");
        Files.lines(Paths.get(Day1.class.getResource("/inputs/day1.txt").toURI()))
                .mapToInt(ints -> findSum(ints, ints.length() / 2))
                .forEach(System.out::println);
    }

    public static int findSum(String ints, int relativeCheckPosition) {
        return IntStream.range(0, ints.length())
                .filter(i -> ints.charAt(i) == ints.charAt((i + relativeCheckPosition) % ints.length()))
                .mapToObj(i -> ints.substring(i, i + 1))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
