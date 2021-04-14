package search;

import java.util.ArrayList;
import java.util.Map;

public interface Search {
    void search(ArrayList<String> list, Map<String, ArrayList<Integer>> searchMap, String query);
}