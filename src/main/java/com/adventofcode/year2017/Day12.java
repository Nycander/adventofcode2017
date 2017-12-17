package com.adventofcode.year2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    public static void main(String... args) throws Exception {
        Path inputPath = Paths.get(Day12.class.getResource("/inputs/day12.txt").toURI());


        Map<String, Set<String>> connections = Files.lines(inputPath)
                .map(str -> str.split(" <-> "))
                .collect(Collectors.toMap(str -> str[0], str -> Stream.of((str[1].split(", "))).collect(Collectors.toSet())));

        System.out.println("Part 1:");
        System.out.println(sizeOfGroup(connections, "0", new HashSet<>()));


        Set<String> globalVisited = new HashSet<>();
        List<Set<String>> groups = new ArrayList<>();

        for (String node : connections.keySet()) {
            if (globalVisited.contains(node)) {
                continue;
            }
            Set<String> group = new HashSet<>();
            sizeOfGroup(connections, node, group);
            groups.add(group);
            globalVisited.addAll(group);
        }
        System.out.println("Part 2:");
        System.out.println(groups.size());
    }

    private static int sizeOfGroup(Map<String, Set<String>> connections, String node, Set<String> visited) {
        if (!visited.add(node)) {
            return 0;
        }
        Set<String> children = connections.get(node);
        if (children == null) {
            throw new IllegalStateException(node);
        }
        return 1 + children.stream()
                .mapToInt(child -> sizeOfGroup(connections, child, visited))
                .sum();
    }
}
