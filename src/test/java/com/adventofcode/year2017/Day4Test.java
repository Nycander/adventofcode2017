package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day4Test {
    @Test
    void samplesDay1() {
        assertTrue(Day4.isValidPassphrase("aa bb cc dd ee"));
        assertFalse(Day4.isValidPassphrase("aa bb cc dd aa"));
        assertTrue(Day4.isValidPassphrase("aa bb cc dd aaa"));
    }

    @Test
    void samplesDay2() {
        assertTrue(Day4.isSecurePassphrase("abcde fghij"));
        assertFalse(Day4.isSecurePassphrase("abcde xyz ecdab"));
        assertTrue(Day4.isSecurePassphrase("a ab abc abd abf abj"));
        assertTrue(Day4.isSecurePassphrase("iiii oiii ooii oooi oooo"));
        assertFalse(Day4.isSecurePassphrase("oiii ioii iioi iiio"));
    }
}