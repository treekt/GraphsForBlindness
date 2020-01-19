package pl.treekt.graphsforblindness.utils;

import pl.treekt.graphsforblindness.exception.ResourceNotInitializedException;

import java.util.ArrayList;

public class DataSetUtil {


    public static String prepareStatement(String label, Integer value, String prefix) {
        return label + " - " + value + " " + prefix;
    }

    public static ArrayList<String> addHintToArray(ArrayList<String> list, String hint) {
        list.add(0, hint);
        return list;
    }
}
