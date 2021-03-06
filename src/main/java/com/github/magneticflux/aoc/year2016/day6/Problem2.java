package com.github.magneticflux.aoc.year2016.day6;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Mitchell on 12/6/2016.
 */
public class Problem2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(Problem2.class.getResourceAsStream("day6.txt"));
        Map<Integer, Multiset<Character>> characters = new HashMap<>();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            for (int i = 0; i < nextLine.length(); i++) {
                if (!characters.containsKey(i))
                    characters.put(i, TreeMultiset.create());
                characters.get(i).add(nextLine.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        characters.forEach((key, value) -> sb.append(
                value.entrySet().stream().min(Comparator.comparingInt(Multiset.Entry::getCount))
                        .get()
                        .getElement()));
        System.out.println(sb);
    }
}
