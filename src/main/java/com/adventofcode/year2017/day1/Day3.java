package com.adventofcode.year2017.day1;

public class Day3 {
    public static void main(String... args) {
        System.out.println(countStepsUntil(1));
        System.out.println(countStepsUntil(12));
        System.out.println(countStepsUntil(23));
        System.out.println(countStepsUntil(1024));
        System.out.println(countStepsUntil(325489));
    }

    private static int countStepsUntil(int goalAddr) {
        int x = 0;
        int y = 0;

        boolean shouldIncreaseLength = false;

        int length = 1;
        int moves = 0;

        Direction direction = Direction.EAST;

        for (int addr = 1; addr < goalAddr; addr++) {
            moves++;

            x += direction.dx;
            y += direction.dy;

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

        return Math.abs(x) + Math.abs(y);
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