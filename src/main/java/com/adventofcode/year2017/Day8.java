package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day8 {
	public static void main(String... args) throws Exception {


		Registry registry = new Registry();

		Files.lines(Paths.get(Day6.class.getResource("/inputs/day8.txt").toURI()))
				.forEach(s -> {
					String[] spl = s.split(" if ");

					Instruction instruction = Instruction.fromString(spl[0]);
					Condition condition = Condition.fromString(spl[1]);

					registry.applyIf(condition, instruction);
				});

		System.out.println("Part 1:");
		System.out.println(registry.getMaxValue());

		System.out.println("Part 2:");
		System.out.println(registry.getTopValue());
	}
}

class Registry {

	private int topValue;

	private Map<String, Integer> registry = new HashMap<>();

	public void applyIf(Condition condition, Instruction instruction) {
		int testValue = getValue(condition.addr);
		if (condition.op.predicate().test(new int[] { testValue, condition.comparable })) {
			int value = getValue(instruction.addr);
			registry.put(instruction.addr, value + instruction.change);
		}

		topValue = Math.max(topValue, getMaxValue());
	}

	int getValue(String addr) {
		return registry.getOrDefault(addr, 0);
	}

	public int getTopValue() {
		return topValue;
	}

	public int getMaxValue() {
		return registry.entrySet().stream()
				.mapToInt(Map.Entry::getValue)
				.max()
				.getAsInt();
	}

	@Override
	public String toString() {
		return "Registry [" +
				"registry=" + registry +
				']';
	}
}

class Instruction {
	String addr;
	int change;

	public Instruction(String addr, int change) {
		this.addr = addr;
		this.change = change;
	}

	static Instruction fromString(String str) {
		String[] spl = str.split("\\s");
		int change = Integer.parseInt(spl[2]);
		if (spl[1].equals("dec")) {
			change *= -1;
		}

		return new Instruction(spl[0], change);
	}

	@Override
	public String toString() {
		return "Instruction [" +
				"addr=" + addr +
				", change=" + change +
				']';
	}
}

class Condition {
	String addr;

	Operator op;
	int comparable;

	public Condition(String addr, Operator op, int comparable) {
		this.addr = addr;
		this.op = op;
		this.comparable = comparable;
	}

	static Condition fromString(String str) {
		String[] spl = str.split("\\s");
		return new Condition(spl[0], Operator.fromString(spl[1]), Integer.parseInt(spl[2]));
	}

	@Override
	public String toString() {
		return "Condition [" +
				"addr=" + addr +
				", op=" + op +
				", comparable=" + comparable +
				']';
	}
}

enum Operator {
	LESS_THEN,
	LESS_THEN_OR_EQUAL,
	MORE_THEN,
	MORE_THEN_OR_EQUAL,
	NOT_EQUAL,
	EQUAL;

	Predicate<int[]> predicate() {
		switch (this) {
			case LESS_THEN:
				return ints -> ints[0] < ints[1];
			case LESS_THEN_OR_EQUAL:
				return ints -> ints[0] <= ints[1];
			case MORE_THEN:
				return ints -> ints[0] > ints[1];
			case MORE_THEN_OR_EQUAL:
				return ints -> ints[0] >= ints[1];
			case NOT_EQUAL:
				return ints -> ints[0] != ints[1];
			case EQUAL:
				return ints -> ints[0] == ints[1];
		}
		throw new IllegalArgumentException();
	}

	static Operator fromString(String str) {
		switch (str.trim()) {
			case ">":
				return MORE_THEN;
			case ">=":
				return MORE_THEN_OR_EQUAL;
			case "<":
				return LESS_THEN;
			case "<=":
				return LESS_THEN_OR_EQUAL;
			case "!=":
				return NOT_EQUAL;
			case "==":
				return EQUAL;
		}
		throw new IllegalArgumentException(str);
	}
}