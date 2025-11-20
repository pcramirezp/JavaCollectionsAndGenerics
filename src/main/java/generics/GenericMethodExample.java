package generics;

import java.util.Arrays;
import java.util.List;

public class GenericMethodExample {


    static void example() {
        genericDisplay(11);

        genericDisplay("Hello World");

        genericDisplay(1.0);
    }

    static <T> void genericDisplay(T element)
    {
        System.out.println(element.getClass().getName()
                + " = " + element);
    }

    static void boundedExample() {
        Integer[] intArray = {1, 2, 3, 4, 5};
        List<Integer> intList = fromArrayToList(intArray);

        System.out.println(intList);

        String[] chatArray = {"one", "two", "tree", "four", "five"};
        List<String> stringList = fromArrayToList(chatArray);

        System.out.println(stringList);

    }

    public static <T extends Comparable> List<T> fromArrayToList(T[] a) {
        return Arrays.asList(a);
    }

    public static void main(String[] args)
    {
        example();
        boundedExample();


    }

}
