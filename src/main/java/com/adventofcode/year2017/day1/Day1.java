package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day1 {
    public static void main(String... args) throws Exception {
        Files.lines(Paths.get(Day1.class.getResource("/inputs/day1.txt").toURI()))
                .mapToInt(Day1::findSum)
                .forEach(System.out::println);
    }

    public static int findSum(String ints) {
        int sum = 0;
        for(int i = 0; i < ints.length(); i++) {
            if (ints.charAt(i) == ints.charAt((i + 1) % ints.length())) {
                sum += Integer.parseInt("" + ints.substring(i, i + 1));
            }
        }
        return sum;
    }
}
