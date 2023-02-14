package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortExample {

    public static void sortingWrapperObjects() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(11);
        integerList.add(2);
        integerList.add(22);
        integerList.add(3);
        integerList.add(33);

        System.out.println(integerList);

        //Collections.sort(integerList);
        integerList.sort(Comparator.naturalOrder());
        System.out.println(integerList);

        //Collections.reverse(integerList);
        integerList.sort(Comparator.reverseOrder());
        System.out.println(integerList);
    }

    public static void sortingUserObjects() {
        List<NumberRepr> numberReprList = new ArrayList<>();
        numberReprList.add(new NumberRepr(1, "one"));
        numberReprList.add(new NumberRepr(11, "eleven"));
        numberReprList.add(new NumberRepr(2, "two"));
        numberReprList.add(new NumberRepr(22, "twenty two"));
        numberReprList.add(new NumberRepr(3, "three"));
        numberReprList.add(new NumberRepr(33, "thirty three"));

        System.out.println(numberReprList);

        //Collections.sort(numberReprList);
        numberReprList.sort(Comparator.comparing(NumberRepr::getStringRepr));
        System.out.println(numberReprList);

        //Collections.reverse(numberReprList);
        numberReprList.sort(Comparator.comparing(NumberRepr::getIntegerRepr).reversed());
        System.out.println(numberReprList);
    }

    public static void main(String[] args) {
        sortingWrapperObjects();
        sortingUserObjects();
    }

    private static class NumberRepr {
        private Integer integerRepr;
        private String stringRepr;

        public NumberRepr(Integer integer, String string) {
            this.integerRepr = integer;
            this.stringRepr = string;
        }

        public Integer getIntegerRepr() {
            return integerRepr;
        }

        public void setIntegerRepr(Integer integerRepr) {
            this.integerRepr = integerRepr;
        }

        public String getStringRepr() {
            return stringRepr;
        }

        public void setStringRepr(String stringRepr) {
            this.stringRepr = stringRepr;
        }

        @Override
        public String toString() {
            return "Numbers [number=" + integerRepr + ", letters=" + stringRepr + "]";
        }

    }
}
