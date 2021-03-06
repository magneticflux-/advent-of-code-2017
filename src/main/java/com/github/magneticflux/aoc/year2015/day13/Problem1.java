package com.github.magneticflux.aoc.year2015.day13;

import com.github.magneticflux.aoc.utils.AdventUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Mitchell on 12/23/2015.
 */
public class Problem1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(Problem1.class.getResourceAsStream("day13.txt"));
        List<String> strings = new LinkedList<>();
        while (input.hasNext())
            strings.add(input.nextLine());
        Pattern p = Pattern.compile("(\\w+) would (lose|gain) (\\d+) happiness units by sitting next to (\\w+)\\.");

        Map<String, Integer> changes = new LinkedHashMap<>();
        Set<String> names = new LinkedHashSet<>();

        for (String s : strings) {
            Matcher m = p.matcher(s);
            if (!m.find())
                throw new RuntimeException("plz");
            String[] parts = new String[]{m.group(1), m.group(2), m.group(3), m.group(4)};
            changes.put(parts[0] + "->" + parts[3], parts[1].equals("gain") ? Integer.parseInt(parts[2]) : -Integer.parseInt(parts[2]));
            names.add(parts[0]);
        }

        List<List<String>> permutations = AdventUtils.permute(new ArrayList<>(names));
        List<String> max = permutations.parallelStream().max(Comparator.comparing(o -> getScore(o, changes))).get();
        System.out.println(max + " " + getScore(max, changes));
    }

    public static Integer getScore(List<String> table, Map<String, Integer> changes) {
        int totalScore = 0;
        for (int i = 1; i < table.size() - 1; i++) {
            totalScore += changes.get(table.get(i) + "->" + table.get(i - 1)); // back/left
            totalScore += changes.get(table.get(i) + "->" + table.get(i + 1)); // front/right
        }

        totalScore += changes.get(table.get(0) + "->" + table.get(table.size() - 1));
        totalScore += changes.get(table.get(0) + "->" + table.get(1));

        totalScore += changes.get(table.get(table.size() - 1) + "->" + table.get(table.size() - 2));
        totalScore += changes.get(table.get(table.size() - 1) + "->" + table.get(0));

        return totalScore;
    }
}
