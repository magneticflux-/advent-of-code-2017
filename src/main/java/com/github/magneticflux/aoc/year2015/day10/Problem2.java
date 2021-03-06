package com.github.magneticflux.aoc.year2015.day10;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mitchell on 12/19/2015.
 */
public class Problem2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(Problem2.class.getResourceAsStream("day10.txt"));
        List<String> strings = new LinkedList<>();
        while (input.hasNext())
            strings.add(input.nextLine());
        String currentString = strings.get(0);

        Pattern p = Pattern.compile("(\\d)\\1*");

        for (int i = 0; i < 50; i++) {
            Matcher m = p.matcher(currentString);
            StringBuilder newString = new StringBuilder();
            while (m.find()) {
                String group = m.group();
                newString.append(group.length()).append(group.charAt(0));
            }
            currentString = newString.toString();
            //System.out.println(currentString);
        }
        System.out.println(currentString.length());
    }
}
