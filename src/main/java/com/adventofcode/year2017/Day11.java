package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Day11 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day11.class.getResource("/inputs/day11.txt").toURI());

        Point point = new Point(0, 0);

        Files.lines(inputPath)
                .flatMap(s -> Stream.of(s.split(",")))
                .map(HexDirection::parse)
                .forEach(point::move);


        System.out.println(point);

        // Ok, so let's BFS to origo
        Map<Point, Point> paths = new HashMap<>();

        Point start = new Point(0, 0);
        Point end = point;
        LinkedList<Point> todo = new LinkedList<>();
        todo.add(start);
        paths.put(start, start);

        do {
            Point current = todo.pollFirst();

            Stream.of(HexDirection.values()).parallel()
                    .forEach(dir -> {
                        Point next = current.copyMoved(dir);
                        if (paths.containsKey(next))
                            return;

                        paths.put(next, current);
                        if (next.equals(end)) {
                            System.out.println(constructPath(paths, next).size() - 1);
                            System.exit(1);

                        } else {
                            todo.addLast(next);
                        }

                    });
        } while (!todo.isEmpty());
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

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point copyMoved(HexDirection dir) {
            Point point = new Point(x, y);
            point.move(dir);
            return point;
        }

        void move(HexDirection hexDir) {
            switch (hexDir) {
                case NORTH:
                    y -= 1;
                    break;
                case SOUTH:
                    y += 1;
                    break;
                case NORTH_EAST:
                case SOUTH_EAST:
                    x += 1;
                    break;
                case NORTH_WEST:
                case SOUTH_WEST:
                    x -= 1;
                    break;
            }

            if (x % 2 == 0) {
                switch (hexDir) {
                    case NORTH_EAST:
                        y -= 1;
                        break;
                    case NORTH_WEST:
                        y -= 1;
                        break;
                }
            } else {
                switch (hexDir) {
                    case SOUTH_EAST:
                        y += 1;
                        break;
                    case SOUTH_WEST:
                        y += 1;
                        break;
                }
            }
        }

        @Override
        public String toString() {
            return x + ";" + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public boolean isOrigo() {
            return x == 0 && y == 0;
        }
    }

    private static class Action {
        Point from;
        Point to;

        HexDirection dir;

        public Action(Point from, Point to, HexDirection dir) {
            this.from = from;
            this.to = to;
            this.dir = dir;
        }

        int length() {
            return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
        }

        @Override
        public String toString() {
            return "Action{" +
                    "from=" + from +
                    ", to=" + to +
                    ", dir=" + dir +
                    '}';
        }
    }
}


enum HexDirection {
    NORTH("n"),
    SOUTH("s"),
    NORTH_EAST("ne"),
    NORTH_WEST("nw"),
    SOUTH_EAST("se"),
    SOUTH_WEST("sw");

    private final String code;

    HexDirection(String code) {
        this.code = code;
    }

    static HexDirection parse(String str) {
        return Stream.of(values()).filter(hd -> hd.code.equals(str)).findFirst().get();
    }
}
