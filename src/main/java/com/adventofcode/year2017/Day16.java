package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day16.class.getResource("/inputs/day16.txt").toURI());

        List<String> dancers = IntStream.range(0, 16)
                .mapToObj(pos -> Character.toString((char) ('a' + pos)))
                .collect(Collectors.toList());

        List<DanceMove> danceMoves = Files.lines(inputPath)
                .flatMap(line -> Stream.of(line.split(",")))
                .map(cmd -> {
                    char move = cmd.charAt(0);
                    String params = cmd.substring(1);
                    switch (move) {
                        case 's':
                            return Spin.parse(params);
                        case 'x':
                            return Exchange.parse(params);
                        case 'p':
                            return Partner.parse(params);
                        default:
                            throw new IllegalArgumentException(cmd);
                    }
                })
                .collect(Collectors.toList());


        System.out.println("Part 1");
        List<String> part1Dancers = new ArrayList<>(dancers);
        danceMoves.forEach(danceMove -> danceMove.apply(part1Dancers));
        System.out.println(part1Dancers.stream().collect(Collectors.joining()));

        System.out.println("Part 2");
        Map<List<String>, List<String>> shortcuts = new ConcurrentHashMap<>();
        for (int i = 0; i < 1_000_000_000; i++) {
            dancers = shortcuts.computeIfAbsent(dancers, key -> {
                List<String> newDancers = new ArrayList<>(key);
                danceMoves.forEach(danceMove -> danceMove.apply(newDancers));
                return newDancers;
            });
        }

        System.out.println(dancers.stream().collect(Collectors.joining()));
    }

    interface DanceMove {
        void apply(List<String> dancers);
    }

    private static class Exchange implements DanceMove {
        private final Integer a;
        private final Integer b;

        public Exchange(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        public static Exchange parse(String substring) {
            String[] split = substring.split("/");
            return new Exchange(Integer.valueOf(split[0]),
                    Integer.valueOf(split[1]));
        }

        @Override
        public void apply(List<String> dancers) {
            String tmp = dancers.get(a);
            dancers.set(a, dancers.get(b));
            dancers.set(b, tmp);
        }
    }

    private static class Partner implements DanceMove {
        private final String a;
        private final String b;

        public Partner(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public static Partner parse(String substring) {
            String[] split = substring.split("/");
            return new Partner(split[0], split[1]);
        }

        @Override
        public void apply(List<String> dancers) {
            int ap = dancers.indexOf(a);
            int bp = dancers.indexOf(b);
            dancers.set(ap, b);
            dancers.set(bp, a);
        }
    }

    private static class Spin implements DanceMove {
        private final int times;

        public Spin(int times) {
            this.times = times;
        }

        public static Spin parse(String substring) {
            return new Spin(Integer.parseInt(substring));
        }

        @Override
        public void apply(List<String> dancers) {
            List<String> old = new ArrayList<>(dancers);

            for (int from = 0; from < dancers.size(); from++) {
                int to = (from + times) % dancers.size();

                dancers.set(to, old.get(from));
            }
        }
    }
}
