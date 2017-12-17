package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day11.class.getResource("/inputs/day11.txt").toURI());

        CubePoint point = new CubePoint(0, 0, 0);

        List<HexDirection> directions = Files.lines(inputPath)
                .flatMap(s -> Stream.of(s.split(",")))
                .map(HexDirection::parse)
                .collect(Collectors.toList());

        int furthestDistance = 0;
        for (HexDirection direction : directions) {
            point = point.apply(direction);
            furthestDistance = Math.max(point.distanceToOrigo(), furthestDistance);
        }

        System.out.println("Part 1:");
        System.out.println(point.distanceToOrigo());

        System.out.println("Part 2:");
        System.out.println(furthestDistance);

    }

    private static List<Point> constructPath(Map<Point, Point> paths, Point end) {
        List<Point> path = new ArrayList<>();
        path.add(end);
        Point current = end;
        while (true) {
            Point next = paths.get(current);
            if (next.equals(current)) {
                break;
            }
            path.add(next);
            current = next;
        }
        return path;
    }

    static class CubePoint {
        private final int x, y, z;

        CubePoint(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        CubePoint apply(HexDirection d) {
            return new CubePoint(x + d.dx, y + d.dy, z + d.dz);
        }

        int distanceToOrigo() {
            return Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
        }
    }
}


enum HexDirection {
    NORTH("n", 0, 1, -1),
    SOUTH("s", 0, -1, 1),
    NORTH_EAST("ne", 1, 0, -1),
    NORTH_WEST("nw", -1, 1, 0),
    SOUTH_EAST("se", 1, -1, 0),
    SOUTH_WEST("sw", -1, 0, 1);

    private final String code;
    final int dx, dy, dz;

    HexDirection(String code, int dx, int dy, int dz) {
        this.code = code;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    static HexDirection parse(String str) {
        return Stream.of(values()).filter(hd -> hd.code.equals(str)).findFirst().get();
    }
}
