package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day13.class.getResource("/inputs/day13.txt").toURI());

        Map<Integer, Scanner> scanners = new HashMap<>();

        Files.lines(inputPath).forEach(s -> {
            String[] spl = s.split(": ");
            Integer depth = Integer.valueOf(spl[0]);
            scanners.put(depth, new Scanner(depth, Integer.parseInt(spl[1])));
        });


        System.out.println("Part 1:");
        System.out.println(getSeverities(scanners, 0).stream().mapToInt(Integer::intValue).sum());

        System.out.println("Part 2");
        System.out.println(getShorterstDelayWithoutDetection(scanners));
    }

    private static int getShorterstDelayWithoutDetection(Map<Integer, Scanner> scanners) {
        for (int delay = 0; delay < 10_000_000; delay++) {
            if (! isDetected(scanners, delay)) {
                return delay;
            }
        }
        throw new RuntimeException();
    }

    private static boolean isDetected(Map<Integer, Scanner> scanners, int delay) {
        int maxDepth = scanners.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();

        for (int depth = 0; depth <= maxDepth; depth++) {
            if (!scanners.containsKey(depth)) {
                continue;
            }
            Scanner scanner = scanners.get(depth);

            if (scanner.positionAtPicoSecond(depth + delay) == 0) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer> getSeverities(Map<Integer, Scanner> scanners, int delay) {
        int maxDepth = scanners.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();

        List<Integer> severities = new ArrayList<>();
        for (int depth = 0; depth <= maxDepth; depth++) {

            if (!scanners.containsKey(depth)) {
                continue;
            }
            Scanner scanner = scanners.get(depth);

            if (scanner.positionAtPicoSecond(depth + delay) == 0) {
                severities.add(scanner.getSeverity());
            }
        }
        return severities;
    }


    static class Scanner {
        private final int depth;
        private final int range;

        Scanner(int depth, int range) {
            this.depth = depth;
            this.range = range;
        }

        int positionAtPicoSecond(int ps) {
            int maxIndex = this.range - 1;
            int offset = ps % (maxIndex * 2);
            return offset > maxIndex ? (2 * maxIndex - offset) : offset;
        }

        int getSeverity() {
            return range * depth;
        }

        @Override
        public String toString() {
            return "Scanner{" +
                    "depth=" + depth +
                    ", range=" + range +
                    '}';
        }

        public String humanToString(int ps) {
            StringBuilder r = new StringBuilder(depth + ": ");
            for (int i = 0; i < range; i++) {
                r.append("[").append(positionAtPicoSecond(ps) == i ? "S" : " ").append("]");
            }
            return r.toString();
        }
    }
}

