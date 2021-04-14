package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnySearch implements Search {

    @Override
    public void search(ArrayList<String> list, Map<String, ArrayList<Integer>> searchMap, String query) {

        String[] words = query.split(" +");
        Set<Integer> toBePrinted = new HashSet<>();

        for (String word : words) {

            word = word.toUpperCase();

            if (searchMap.containsKey(word)) {
                toBePrinted.addAll(searchMap.get(word));
            }
        }

        if (toBePrinted.isEmpty()) {
            System.out.println("No matching person found.");
        } else {
            System.out.println();
            System.out.println(toBePrinted.size() + " persons found:");
            for (Integer i : toBePrinted) {
                System.out.println(list.get(i));
            }
        }
    }
}
