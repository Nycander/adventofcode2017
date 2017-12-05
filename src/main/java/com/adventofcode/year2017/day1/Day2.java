package com.adventofcode.year2017.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String... args) throws Exception {
        System.out.println("Part 1");
        System.out.println(input()
                .map(s -> Arrays.stream(s.split("\\s"))
                        .mapToInt(Integer::parseInt)
                        .collect(
                                MinMax::new,
                                MinMax::update,
                                MinMax::combine
                        )
                )
                .mapToInt(MinMax::span)
                .sum());


        System.out.println("Part 2");
        System.out.println(input()
                .mapToInt(s -> {
                            List<Integer> nums = Arrays.stream(s.split("\\s"))
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList());
                            for (int i = 0; i < nums.size(); i++) {
                                Integer a = nums.get(i);
                                for (int j = 0; j < nums.size(); j++) {
                                    if (i == j) continue;

                                    Integer b = nums.get(j);
                                    if (a % b == 0) {
                                        return a / b;
                                    }
                                }
                            }
                            return 0;
                        }
                )
                .sum());
    }

    private static Stream<String> input() throws IOException, URISyntaxException {
        return Files.lines(Paths.get(Day2.class.getResource("/inputs/day2.txt").toURI()));
    }
}

class MinMax {
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    MinMax() {
        this(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    private MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    void update(int value) {
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    void combine(MinMax minMax2) {
        min = Math.min(min, minMax2.min);
        max = Math.max(max, minMax2.max);
    }

    int span() {
        return max - min;
    }
}