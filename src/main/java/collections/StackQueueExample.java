package collections;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StackQueueExample {

    public static void stackExample() {
        Stack<Integer> integerStack = new Stack<Integer>();
        integerStack.add(1);
        integerStack.add(2);
        integerStack.add(3);
        System.out.println("Actual stack " + integerStack);
        System.out.println("Get data from the stack " + integerStack.pop());
        System.out.println("Actual stack " + integerStack);
        System.out.println("Get data from the stack " + integerStack.pop());
        integerStack.add(4);
        integerStack.add(5);
        System.out.println("Actual stack " + integerStack);
        System.out.println("Observing stack data " + integerStack.peek());
        System.out.println("Get data from the stack " + integerStack.pop());
        System.out.println("Actual stack " + integerStack);

    }

    public static void queueExample() {
        // Cola implementandas con Clase Queue de Java
        Queue<Integer> integerQueue = new LinkedList<Integer>();
        integerQueue.offer(1);
        integerQueue.offer(2);
        integerQueue.offer(3);
        System.out.println("Actual Queue " + integerQueue);
        System.out.println("Get data from the queue " + integerQueue.poll());
        System.out.println("Actual Queue " + integerQueue);
        System.out.println("Get data from the queue " + integerQueue.poll());
        integerQueue.offer(4);
        integerQueue.offer(5);
        System.out.println("Actual Queue " + integerQueue);
        System.out.println("Observing queue data" + integerQueue.peek());
        System.out.println("Actual Queue  " + integerQueue);
        System.out.println("Get data from the queue " + integerQueue.poll());
        System.out.println("Actual Queue " + integerQueue);

    }

    public static void main(String[] args) {
        stackExample();
        queueExample();
    }
}
