package com.github.magneticflux.aoc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mitchell on 12/23/2015.
 */
public class AdventUtils {
    public static void main(String[] args) {
        permute(Arrays.asList("a", "b", "c")).forEach(System.out::println);
    }

    public static <E> List<List<E>> permute(List<E> arr) {
        return permute(arr, 0);
    }

    public static <E> List<List<E>> permute(List<E> arr, int k) {
        List<List<E>> permutations = new ArrayList<>();
        for (int i = k; i < arr.size(); i++) {
            Collections.swap(arr, i, k);
            permutations.addAll(permute(arr, k + 1));
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() - 1) {
            permutations.add(new ArrayList<>(arr));
        }
        return permutations;
    }
}
