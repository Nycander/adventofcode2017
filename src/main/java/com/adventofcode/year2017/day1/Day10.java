package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day10.class.getResource("/inputs/day10.txt").toURI());
/*
        System.out.println("Part 1:");
        HashState hashState = new HashState();

        Files.lines(inputPath)
                .flatMap(s -> Stream.of(s.split(",")))
                .map(Integer::parseInt)
                .forEach(hashState::reverseRound);

        System.out.println(hashState.list[0] * hashState.list[1]);
*/
        System.out.println("Part 2:");
        HashState part2 = new HashState();

        List<Integer> lengths = Stream.concat(
                Files.lines(inputPath).flatMap(s -> s.chars().boxed()),
                Stream.of(17, 31, 73, 47, 23)
        ).collect(Collectors.toList());

        for (int rounds = 0; rounds < 64; rounds++) {
            lengths.forEach(part2::reverseRound);
        }

        System.out.println(part2.getDenseHash());

    }
}

class HashState {
    int[] list = new int[256];
    int currentPosition = 0;
    int skipSize = 0;

    public HashState() {
        for (int i = 0; i <= 255; i++) {
            list[i] = i;
        }
    }

    void reverseRound(int length) {
        for (int i = currentPosition, j = currentPosition + length - 1;
             i <= j; i++, j--) {

            int ic = clamp(i, list.length);
            int jc = clamp(j, list.length);

            int tmp = list[ic];
            list[ic] = list[jc];
            list[jc] = tmp;
        }
        currentPosition = (currentPosition + length + skipSize) % list.length;
        skipSize++;
    }

    int clamp(int index, int length) {
        return index % length;
    }

    public String getDenseHash() {
        int[] hash = new int[16];
        for (int block = 0; block < list.length; block += 16) {
            for (int i = block; i < block + 16; i++) {
                hash[block / 16] ^= list[i];
            }
        }
        return IntStream.of(hash)
                .mapToObj(h -> String.format("%02x", h))
                .collect(Collectors.joining());
    }
}