package generics;

public class GenericClassExample {

    public static void main(String[] args)
    {
        Test<Integer, Integer> firstObject = new Test<>(15, 20);
        System.out.println(firstObject.getObj1() +" "+ firstObject.getObj2());

        Test<String, Integer> secondObject = new Test<>("Hello World", 15);
        System.out.println(secondObject.getObj1() +" "+ secondObject.getObj2());

        // This results an error
        //firstObject = secondObject;

    }


    public static class Test<T, U> {
        private T obj1;
        private U obj2;

        public Test(T obj1, U obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        public T getObj1() {
            return obj1;
        }

        public U getObj2() {
            return obj2;
        }
    }
}
