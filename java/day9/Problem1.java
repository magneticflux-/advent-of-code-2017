package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Mitchell on 12/19/2015.
 */
public class Problem1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("day9.txt"));
        List<String> strings = new LinkedList<>();
        while (input.hasNext())
            strings.add(input.nextLine());

        Map<Route, Integer> routes = new LinkedHashMap<>(strings.size());
        for (String s : strings) {
            String[] temp = s.split("( to | = )");
            routes.put(new Route(temp[0], temp[1]), Integer.parseInt(temp[2]));
        }
        Set<String> locations = new LinkedHashSet<>();
        for (Route route : routes.keySet()) {
            locations.add(route.from);
            locations.add(route.to);
        }

        //System.out.println(routes);
        //System.out.println(locations);

        List<List<String>> possiblePaths = permute(new ArrayList<>(locations), 0);
        //System.out.println(possiblePaths.size());

        Stream<TotalRoute> totalRoutes = possiblePaths.parallelStream().map(strings1 -> {
            int length = 0;
            for (int i = 0; i < strings1.size() - 1; i++) {
                try {
                    length += routes.get(new Route(strings1.get(i), strings1.get(i + 1)));
                } catch (NullPointerException e) {
                    System.out.println("Illegal path from " + strings1.get(i) + " to " + strings1.get(i + 1));
                    length += 1337 * 42; // Invalid path is heavily penalized
                }
            }
            return new TotalRoute(strings1, length);
        });
        TotalRoute min = totalRoutes.collect(Collectors.minBy(new Comparator<TotalRoute>() {
            @Override
            public int compare(TotalRoute o1, TotalRoute o2) {
                return Integer.compare(o1.length, o2.length);
            }
        })).get();
        StringBuilder out = new StringBuilder();
        for (String s : min.stops) {
            out.append(s);
            out.append(" -> ");
        }
        out.delete(out.length() - 4, out.length());
        out.append(" takes ").append(min.length).append(" units.");
        System.out.println(out);
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

class TotalRoute {
    final List<String> stops;
    final int length;

    public TotalRoute(List<String> stops, int length) {
        this.stops = Collections.unmodifiableList(stops);
        this.length = length;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TotalRoute && this.stops.equals(((TotalRoute) obj).stops) && this.length == ((TotalRoute) obj).length;
    }

    @Override
    public String toString() {
        return stops + " : " + length;
    }

    @Override
    public int hashCode() {
        return (stops.toString() + length).hashCode();
    }
}

class Route {
    final String from;
    final String to;

    public Route(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Route && ((Objects.equals(((Route) obj).from, this.from) && Objects.equals(((Route) obj).to, this.to)) || (Objects.equals(((Route) obj).from, this.to) && Objects.equals(((Route) obj).to, this.from)));
    }

    @Override
    public String toString() {
        return "Route between " + from + " and " + to;
    }

    @Override
    public int hashCode() {
        return from.hashCode() & to.hashCode();
    }
}
