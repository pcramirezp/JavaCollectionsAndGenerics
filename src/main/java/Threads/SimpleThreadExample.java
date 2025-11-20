package Threads;

import java.util.Random;

public class SimpleThreadExample {

    // Variable compartida que indica cuando detener los hilos
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker1 = new Thread(() -> doWork("Worker-1"), "Worker-1");
        Thread worker2 = new Thread(() -> doWork("Worker-2"), "Worker-2");
        Thread worker3 = new Thread(() -> doWork("Worker-3"), "Worker-3");

        worker1.start();
        worker2.start();
        worker3.start();

        System.out.println("\nHilos iniciados...\n");

        // Esperamos a que cada hilo termine cuando le toque
        worker1.join();
        worker2.join();
        worker3.join();

        System.out.println("\nTodos los threads han terminado.");
    }

    private static void doWork(String name) {
        Random random = new Random();
        int workTime = random.nextInt(3000) + 1000; // entre 1 y 4 segundos

        long start = System.currentTimeMillis();
        System.out.println(name + " — tiempo de trabajo: " + workTime + " ms");

        try {
            while (System.currentTimeMillis() - start < workTime) {
                System.out.println(name + " trabajando...");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(name + " terminó de forma aleatoria.");
    }

}
