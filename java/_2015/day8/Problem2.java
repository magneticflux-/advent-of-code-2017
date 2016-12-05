package _2015.day8;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mitchell on 12/19/2015.
 */
public class Problem2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(Problem2.class.getResourceAsStream("day8.txt"));
        List<String> strings = new LinkedList<>();
        while (input.hasNext())
            strings.add(input.nextLine());

        int codeSize = strings.parallelStream().mapToInt(String::length).sum();
        int newStringSize = strings.parallelStream().mapToInt(value -> value.replaceAll("(\"|\\\\)", "__").length() + 2).sum();

        System.out.println(newStringSize - codeSize);
    }
}
