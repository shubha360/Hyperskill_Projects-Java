package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoneSearch implements Search {

    @Override
    public void search(ArrayList<String> list, Map<String, ArrayList<Integer>> searchMap, String query) {

        String[] words = query.split(" +");
        Set<Integer> toBeRemoved = new HashSet<>();

        for (String word : words) {

            word = word.toUpperCase();
            if (searchMap.containsKey(word)) {
                toBeRemoved.addAll(searchMap.get(word));
            }
        }

        if (toBeRemoved.isEmpty()) {
            System.out.println("No matching person found.");
        } else {

            System.out.println();
            System.out.println((list.size() - toBeRemoved.size()) + " persons found:");
            for (int i = 0; i < list.size(); i++) {

                if (!toBeRemoved.contains(i)) {
                    System.out.println(list.get(i));
                }
            }
        }
    }
}
