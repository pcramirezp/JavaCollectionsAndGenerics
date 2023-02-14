package collections;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetExample {

      public static void hashSetExample() {
        Set<String> hashSetEx = new HashSet<>();
        hashSetEx.add("element 3");
        hashSetEx.add("element 0");
        hashSetEx.add("element 1");
        hashSetEx.add("element 4");
        hashSetEx.add("element 4");
        hashSetEx.add("element 2");
        hashSetEx.add("element 5");
        hashSetEx.add("element 0");

        System.out.println("HashSet");

        for(String object : hashSetEx) {
            String element = object;
            System.out.println(element);
        }
        System.out.println();
    }

    public static void linkedHashSetExample() {
        Set<String> linkedHashSetEx = new LinkedHashSet<>();
        linkedHashSetEx.add("element 3");
        linkedHashSetEx.add("element 0");
        linkedHashSetEx.add("element 1");
        linkedHashSetEx.add("element 4");
        linkedHashSetEx.add("element 4");
        linkedHashSetEx.add("element 2");
        linkedHashSetEx.add("element 5");
        linkedHashSetEx.add("element 0");

        System.out.println("LinkedHashSet");

        for(String object : linkedHashSetEx) {
            String element = object;
            System.out.println(element);
        }
        System.out.println();
    }

    public static void treeSetExample() {
        Set<String> treeSetEx = new TreeSet<>();
        treeSetEx.add("element 3");
        treeSetEx.add("element 0");
        treeSetEx.add("element 1");
        treeSetEx.add("element 4");
        treeSetEx.add("element 4");
        treeSetEx.add("element 2");
        treeSetEx.add("element 5");
        treeSetEx.add("element 0");

        System.out.println("TreeSet");
        for(String object : treeSetEx) {
            String element = object;
            System.out.println(element);
        }
        System.out.println();

    }

    public static void main(String[] args) {
        hashSetExample();
        linkedHashSetExample();
        treeSetExample();
    }

}
