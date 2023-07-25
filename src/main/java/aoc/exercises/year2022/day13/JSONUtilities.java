package aoc.exercises.year2022.day13;

import org.json.JSONArray;

public class JSONUtilities {
    private JSONUtilities() {
    }

    public static int compare(Object item1, Object item2) {
        if (item1 instanceof JSONArray || item2 instanceof JSONArray) {
            return compareArray(getJSONArray(item1), getJSONArray(item2));
        } else {
            return ((Integer) item1).compareTo((Integer) item2);
        }
    }

    private static JSONArray getJSONArray(Object item) {
        if (item instanceof JSONArray jsonArray) {
            return jsonArray;
        } else {
            JSONArray newJSONArray = new JSONArray();
            newJSONArray.put(item);
            return newJSONArray;
        }
    }

    private static int compareArray(JSONArray item1JSON, JSONArray item2JSON) {
        int isOrdered = 0;
        // We need the index to read item2JSON in parallel
        for (int index = 0; index < item1JSON.length(); index++) {
            // item1JSON contains item2Json but is bigger
            if (item2JSON.length() == index) {
                return 1;
            }
            isOrdered += compare(item1JSON.get(index), item2JSON.get(index));
            // item1JSON is different from item2JSON
            if (isOrdered != 0) {
                return isOrdered;
            }
        }
        // item2JSON contains item1Json
        return -1;
    }
}
