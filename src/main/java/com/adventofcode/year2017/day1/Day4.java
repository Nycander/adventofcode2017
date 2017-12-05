package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String... args) throws Exception {
        System.out.println("Part 1:");

        System.out.println(Files.lines(Paths.get(Day4.class.getResource("/inputs/day4.txt").toURI()))
                .filter(Day4::isValidPassphrase)
                .count());

        System.out.println("Part 2:");

        System.out.println(Files.lines(Paths.get(Day4.class.getResource("/inputs/day4.txt").toURI()))
                .filter(Day4::isSecurePassphrase)
                .count());
    }

    static boolean isValidPassphrase(String passphrase) {
        String[] words = passphrase.split("\\s");
        return words.length == Stream.of(words).distinct().count();
    }

    static boolean isSecurePassphrase(String passphrase) {
        String[] words = passphrase.split("\\s");
        return words.length == Stream.of(words)
                .map(word -> word.chars().sorted().mapToObj(ch -> Character.toString((char) ch)).collect(Collectors.joining()))
                .distinct()
                .count();
    }
}
