package com.adventofcode.year2017;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class KnotHash {
    int[] list;
    private int currentPosition = 0;
    private int skipSize = 0;

    public KnotHash(int bits) {
        this.list = new int[bits];
        for (int i = 0; i < bits; i++) {
            list[i] = i;
        }
    }

    void run(String chars) {
        for (int round = 0; round < 64; round++) {
            Stream.concat(toChars(chars), Stream.of(17, 31, 73, 47, 23))
                    .forEach(this::reverseRound);
        }
    }

    private static Stream<Integer> toChars(String s) {
        return IntStream.range(0, s.length())
                .map(s::charAt)
                .boxed();
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
        int[] hash = new int[list.length / 16];
        for (int block = 0; block < list.length; block += 16) {
            for (int i = block; i < block + 16; i++) {
                hash[block / 16] ^= list[i];
            }
        }
        return IntStream.of(hash)
                .mapToObj(h -> String.format("%02x", h))
                .collect(Collectors.joining());
    }

    public boolean[] getBinary() {
        String denseHash = getDenseHash();
        boolean[] bin = new boolean[denseHash.length() * 4];

        char[] charArray = denseHash.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int hexValue = hexToInt(charArray[i]);
            bin[i * 4 + 0] = (hexValue & 0b1000) > 0;
            bin[i * 4 + 1] = (hexValue & 0b0100) > 0;
            bin[i * 4 + 2] = (hexValue & 0b0010) > 0;
            bin[i * 4 + 3] = (hexValue & 0b0001) > 0;
        }
        return bin;
    }

    private int hexToInt(char hex) {
        switch (hex) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
        }
        throw new IllegalArgumentException(hex + " is invalid hex");
    }
}