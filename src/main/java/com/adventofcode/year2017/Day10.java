package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day10.class.getResource("/inputs/day10.txt").toURI());
        System.out.println("Part 1:");
        KnotHash knotHash = new KnotHash(256);

        Files.lines(inputPath)
                .flatMap(s -> Stream.of(s.split(",")))
                .map(Integer::parseInt)
                .forEach(knotHash::reverseRound);

        System.out.println(knotHash.list[0] * knotHash.list[1]);

        System.out.println("Part 2:");
        KnotHash part2 = new KnotHash(256);

        part2.run(Files.lines(inputPath).collect(Collectors.joining()));

        System.out.println(part2.getDenseHash());

    }

    private static Stream<Integer> toChars(String s) {
        return IntStream.range(0, s.length())
                .map(s::charAt)
                .boxed();
    }
}
