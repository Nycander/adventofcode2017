package com.adventofcode.year2017.day1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day3 {
    public static void main(String... args) {
        System.out.println(countStepsUntil(1));
        System.out.println(countStepsUntil(12));
        System.out.println(countStepsUntil(23));
        System.out.println(countStepsUntil(1024));
        System.out.println(countStepsUntil(325489));


        System.out.println(stressTest(4));
        System.out.println(stressTest(325489));
    }

    private static int stressTest(int goalValue) {
        Map<Point, Integer> values = new HashMap<>();
        values.put(Point.origo(), 1);

        Point position = new Point(0, 0);

        boolean shouldIncreaseLength = false;

        int length = 1;
        int moves = 0;

        Direction direction = Direction.EAST;

        for (int addr = 1, currentValue = addr; true; addr++) {
            moves++;

            position = position.move(direction);

            currentValue = sumNeighbours(position, values);
            values.put(position, currentValue);

            if (moves >= length) {
                moves = 0;

                direction = direction.turnLeft();

                if (shouldIncreaseLength) {
                    length++;
                    shouldIncreaseLength = false;
                } else {
                    shouldIncreaseLength = true;
                }
            }

            if (currentValue > goalValue) {
                return currentValue;
            }
        }
    }

    private static int sumNeighbours(Point position, Map<Point, Integer> values) {
        int sum = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                sum += values.getOrDefault(position.move(dx, dy), 0);
            }
        }
        return sum;
    }

    private static int countStepsUntil(int goalAddr) {
        Point position = new Point(0, 0);

        boolean shouldIncreaseLength = false;

        int length = 1;
        int moves = 0;

        Direction direction = Direction.EAST;

        for (int addr = 1; addr < goalAddr; addr++) {
            moves++;

            position = position.move(direction);

            if (moves >= length) {
                moves = 0;

                direction = direction.turnLeft();

                if (shouldIncreaseLength) {
                    length++;
                    shouldIncreaseLength = false;
                } else {
                    shouldIncreaseLength = true;
                }
            }
        }

        return position.distanceToOrigin();
    }
}

enum Direction {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), LEFT(-1, 0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    Direction turnRight() {
        Direction[] values = Direction.values();
        return values[(this.ordinal() + 1) & 3];
    }

    Direction turnLeft() {
        Direction[] values = Direction.values();
        return values[(this.ordinal() - 1) & 3];
    }
}

class Point {
    public final int x;
    public final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point move(Direction d) {
        return move(d.dx, d.dy);
    }

    Point move(int dx, int dy) {
        return new Point(x + dx, y + dy);
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
    public String toString() {
        return x + "," + y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int distanceToOrigin() {
        return Math.abs(x) + Math.abs(y);
    }

    public static Point origo() {
        return new Point(0, 0);
    }
}