package collections;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapExample {

    public static void hashMapExample(){
        Map<String, String> hashMapEx = new HashMap<>();
        hashMapEx.put("key1", "element 3");
        hashMapEx.put("key4", "element 4");
        hashMapEx.put("key2", "element 0");
        hashMapEx.put("key3", "element 1");
        hashMapEx.put("key3", "element 2");
        hashMapEx.put("key5", "element 4");

        System.out.println("HashMap");
        System.out.println(hashMapEx.get("key3"));

        for (String object : hashMapEx.keySet()) {
            String key = object;
            System.out.println(key + " " + hashMapEx.get(key));
        }
        System.out.println();

    }

    public static void treeMapExample(){
        Map<String, String> treeMapEx = new TreeMap<>();
        treeMapEx.put("key1", "element 3");
        treeMapEx.put("key4", "element 4");
        treeMapEx.put("key2", "element 0");
        treeMapEx.put("key3", "element 1");
        treeMapEx.put("key3", "element 2");
        treeMapEx.put("key5", "element 4");

        System.out.println("TreeMap");

        System.out.println(treeMapEx.get("key3"));

        for (String object : treeMapEx.keySet()) {
            String element = object;
            System.out.println(element + " " + treeMapEx.get(element));
        }

        System.out.println();
    }

    public static void main(String[] args) {
        hashMapExample();
        treeMapExample();
    }

}
