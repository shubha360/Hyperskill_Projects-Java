package search;

import java.util.ArrayList;
import java.util.Map;

public class AllSearch implements Search {

    @Override
    public void search(ArrayList<String> list, Map<String, ArrayList<Integer>> searchMap, String query) {

        String[] words = query.split(" +");
        ArrayList<String> found = new ArrayList<>();

        for (String line : list) {

            boolean matchFound = true;

            for (String word : words) {

                if (!line.toUpperCase().contains(word.toUpperCase())) {
                    matchFound = false;
                    break;
                }
            }

            if (matchFound) {
                found.add(line);
            }
        }

        if (found.isEmpty()) {
            System.out.println("No matching person found.");
        } else {

            System.out.println();
            System.out.println(found.size() + " persons found:");
            found.forEach(System.out::println);
        }
    }
}
