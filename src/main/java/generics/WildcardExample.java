package generics;

import java.util.Arrays;
import java.util.List;

public class WildcardExample {


    public static void upperBoundedExample() {
        // Upper Bounded Integer List
        List<Integer> list1 = Arrays.asList(4, 5, 6, 7);

        // printing the sum of elements in list
        System.out.println("Total sum is:" + sum(list1));

        // Double list
        List<Double> list2 = Arrays.asList(4.1, 5.1, 6.1);

        // printing the sum of elements in list
        System.out.println("Total sum is:" + sum(list2));
    }
    private static double sum(List<? extends Number> list)
    {
        double sum = 0.0;
        for (Number i : list) {
            sum += i.doubleValue();
        }

        return sum;
    }

    private static void lowerBoundedExample() {

        // Lower Bounded Integer List
        List<Integer> list1 = Arrays.asList(4, 5, 6, 7);

        // Integer list object is being passed
        printOnlyIntegerClassorSuperClass(list1);

        // Number list
        List<Number> list2 = Arrays.asList(4, 5, 6, 7);

        // Integer list object is being passed
        printOnlyIntegerClassorSuperClass(list2);

        // Number list
        List<Double> list3 = Arrays.asList(4.1, 5.1, 6.1);

        // Double list object is not being passed
        //printOnlyIntegerClassorSuperClass(list3);
    }

    public static void printOnlyIntegerClassorSuperClass(List<? super Integer> list)
    {
        System.out.println(list);
    }

    public static void unboundedExample() {
        // Integer List
        List<Integer> list1 = Arrays.asList(1, 2, 3);

        // Double list
        List<Double> list2 = Arrays.asList(1.1, 2.2, 3.3);

        printlist(list1);

        printlist(list2);
    }

    private static void printlist(List<?> list)
    {
        System.out.println(list);
    }

    public static void main(String[] args)
    {
        upperBoundedExample();
        lowerBoundedExample();
        unboundedExample();

    }




}
