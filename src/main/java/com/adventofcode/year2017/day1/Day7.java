package com.adventofcode.year2017.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day7 {
    public static void main(String... args) throws Exception {
        Map<String, Node> nodes = new HashMap<>();

        Map<String, String> edges = new HashMap<>();

        Files.lines(Paths.get(Day7.class.getResource("/inputs/day7.txt").toURI()))
                .forEach(s -> {
                    String[] split = s.split(" -> ");

                    String[] nameAndWeight = split[0].split(" \\(");
                    String name = nameAndWeight[0];
                    int weight = Integer.parseInt(nameAndWeight[1].substring(0, nameAndWeight[1].length() - 1));
                    Node node = new Node(name, weight);

                    if (split.length > 1) {
                        String[] children = split[1].split("\\s*,\\s*");

                        node.children = children;

                        Stream.of(children).forEach(child -> edges.put(child, name));
                    }
                    nodes.put(name, node);
                });

        edges.forEach((child, parent) -> {
            nodes.get(child).parent = parent;
        });

        System.out.println("Part 1:");

        nodes.values().stream()
                .filter(e -> e.children.length > 0)
                .filter(e -> e.parent == null)
                .map(e -> e.name)
                .forEach(System.out::println);


        System.out.println("Part 2:");

        nodes.values().stream()
                .filter(node -> !node.childrenHasCorrectWeight(nodes))
                .forEach(System.out::println);
    }
}


class Node {
    String name;
    int weight;

    String[] children = new String[0];
    String parent;

    public Node(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    boolean childrenHasCorrectWeight(Map<String, Node> nodes) {
        return children.length == 0 ||
                Stream.of(children)
                        .map(nodes::get)
                        .mapToInt(node -> node.weight)
                        .distinct().count() == 1;

    }

    int getDiffWeight(Map<String, Node> nodes) {
        int[] weights = Stream.of(children)
                .map(nodes::get)
                .mapToInt(node -> node.weight)
                .toArray();
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", children=" + Arrays.toString(children) +
                ", parent='" + parent + '\'' +
                '}';
    }
}