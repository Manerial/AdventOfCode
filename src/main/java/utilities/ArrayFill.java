package utilities;

import java.util.Arrays;

public class ArrayFill {
    private ArrayFill() {}

    /**
     * Creates an array with a size and a default value for each item
     *
     * @param size : the size of the final array
     * @param value : the initial value (integer)
     * @return an array containing of the desired size containing only the desired value
     */
    public static Integer[] initIntArray(int size, int value) {
        Integer[] newArray = new Integer[size];
        Arrays.fill(newArray, value);
        return newArray;
    }

    /**
     * Creates an array with a size and a default value for each item
     *
     * @param size : the size of the final array
     * @param value : the initial value (String)
     * @return an array containing of the desired size containing only the desired value
     */
    public static String[] initStringArray(int size, String value) {
        String[] newArray = new String[size];
        Arrays.fill(newArray, value);
        return newArray;
    }
}
