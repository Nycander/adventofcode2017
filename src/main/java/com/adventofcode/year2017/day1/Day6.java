package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
	public static void main(String... args) throws Exception {
		System.out.println("Part 1:");
		Files.lines(Paths.get(Day6.class.getResource("/inputs/day6.txt").toURI()))
				.map(s -> Stream.of(s.split("\\s")).map(Integer::parseInt).collect(Collectors.toList()))
				.mapToInt(Day6::countRedistributionCycles)
				.forEach(System.out::println);

		System.out.println("Part 2:");
		Files.lines(Paths.get(Day6.class.getResource("/inputs/day6.txt").toURI()))
				.map(s -> Stream.of(s.split("\\s")).map(Integer::parseInt).collect(Collectors.toList()))
				.mapToInt(Day6::countLoopSize)
				.forEach(System.out::println);
	}

	public static int countRedistributionCycles(List<Integer> memoryBanks) {
		Set<List<Integer>> seenDistributions = new HashSet<>();

		int steps = 0;
		while (true) {
			seenDistributions.add(new ArrayList<>(memoryBanks));

			reallocate(memoryBanks);
			steps++;

			if (seenDistributions.contains(memoryBanks)) {
				break;
			}
		}

		return steps;
	}

	public static int countLoopSize(List<Integer> memoryBanks) {
		Set<List<Integer>> seenDistributions = new HashSet<>();

		boolean seenOnce = false;

		int loopCounter = 0;
		while (true) {
			seenDistributions.add(new ArrayList<>(memoryBanks));

			reallocate(memoryBanks);

			if (seenOnce) {
				loopCounter++;
			}

			if (seenDistributions.contains(memoryBanks)) {
				if (seenOnce) {
					break;
				} else {
					seenOnce = true;
					seenDistributions.clear();
				}
			}
		}

		return loopCounter;
	}

	private static void reallocate(List<Integer> memoryBanks) {
		int fromIndex = findMaxIndex(memoryBanks);
		int memory = memoryBanks.get(fromIndex);
		memoryBanks.set(fromIndex, 0);

		for (int i = 0, index = nextIndex(memoryBanks, fromIndex); i < memory; i++) {
			memoryBanks.set(index, memoryBanks.get(index) + 1);
			index = nextIndex(memoryBanks, index);
		}
	}

	private static int nextIndex(List<Integer> memoryBanks, int index) {
		return (index + 1) & (memoryBanks.size() - 1);
	}

	private static int findMaxIndex(List<Integer> memoryBanks) {
		int max = memoryBanks.stream().mapToInt(Integer::intValue).max().getAsInt();

		for (int i = 0; i < memoryBanks.size(); i++) {
			if (memoryBanks.get(i) == max) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
}
