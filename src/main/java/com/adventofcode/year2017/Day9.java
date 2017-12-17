package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class Day9 {
    public static void main(String... args) throws Exception {

        String input = Files.lines(Paths.get(Day9.class.getResource("/inputs/day9.txt").toURI())).findFirst().get();

        int currentScore = 0;

        int totalGarbage = 0;
        int totalScore = 0;

        State state = State.GROUP;
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            Token token = state.parseInput.apply(c);

            switch (token) {
                case START_GARBAGE:
                    state = State.GARBAGE;
                    break;
                case GARBAGE:
                    totalGarbage++;
                    break;
                case END_GARBAGE:
                    state = State.GROUP;
                    break;
                case START_GROUP:
                    state = State.GROUP;
                    currentScore++;
                    break;
                case IGNORE_NEXT:
                    i++;
                    break;
                case END_GROUP:
                    totalScore += currentScore;
                    currentScore--;
                case NO_OP:
                    break;
            }
        }

        System.out.println("Part 1:");
        System.out.println(totalScore);

        System.out.println("Part 2:");
        System.out.println(totalGarbage);
    }
}


enum Token {
    START_GARBAGE,
    GARBAGE,
    END_GARBAGE,

    START_GROUP,
    END_GROUP,

    IGNORE_NEXT,

    NO_OP
}

enum State {
    GARBAGE((c) -> {
        switch (c) {
            case '>':
                return Token.END_GARBAGE;
            case '!':
                return Token.IGNORE_NEXT;
            default:
                return Token.GARBAGE;
        }
    }), GROUP((c) -> {
        switch (c) {
            case '}':
                return Token.END_GROUP;
            case '{':
                return Token.START_GROUP;
            case '<':
                return Token.START_GARBAGE;
            case '!':
                return Token.IGNORE_NEXT;
            default:
                return Token.NO_OP;
        }
    });

    final Function<Character, Token> parseInput;

    State(Function<Character, Token> parseInput) {
        this.parseInput = parseInput;
    }
}