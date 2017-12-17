package com.adventofcode.year2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day14 {
    public static void main(String... args) {
        String seed = "amgozmfv";

        KnotHash[] disk = new KnotHash[128];
        for (int i = 0; i < disk.length; i++) {
            disk[i] = new KnotHash(256);
            disk[i].run(seed + "-" + i);
        }

        System.out.println("Part 1");
        System.out.println(Stream.of(disk)
                .mapToInt(knotHash -> {
                    int sum = 0;
                    for (boolean b : knotHash.getBinary()) {
                        sum += b ? 1 : 0;
                    }
                    return sum;
                }).sum());

        System.out.println("Part 2");

        boolean[][] grid = new boolean[128][128];
        for (int i = 0; i < disk.length; i++) {
            boolean[] binary = disk[i].getBinary();
            for (int j = 0; j < binary.length; j++) {
                grid[i][j] = binary[j];
            }
        }

        int regionCount = 0;
        boolean[][] seenRegions = new boolean[128][128];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!grid[i][j]) continue;
                if (seenRegions[i][j]) continue;

                markRegion(seenRegions, i, j, grid);
                regionCount++;
            }
        }

        System.out.println(regionCount);
    }

    private static void markRegion(boolean[][] seenRegions, int i, int j, boolean[][] grid) {
        seenRegions[i][j] = true;
        findNeighbours(grid, i, j).stream()
                .filter(point -> !seenRegions[point.i][point.j])
                .filter(point -> grid[point.i][point.j])
                .forEach(point -> markRegion(seenRegions, point.i, point.j, grid));
    }

    private static List<Point> findNeighbours(boolean[][] grid, int i, int j) {
        List<Point> neighbours = new ArrayList<>();
        if (j > 0) {
            neighbours.add(new Point(i, j - 1));
        }
        if (j < grid[i].length - 1) {
            neighbours.add(new Point(i, j + 1));
        }
        if (i > 0) {
            neighbours.add(new Point(i - 1, j));
        }
        if (i < grid.length - 1) {
            neighbours.add(new Point(i + 1, j));
        }
        return neighbours;
    }

    private static class Point {
        private final int i, j;

        private Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return i == point.i &&
                    j == point.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
