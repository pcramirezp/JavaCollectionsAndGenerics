package Threads;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample {
    private static final Semaphore semaphore = new Semaphore(2); // máx 2 hilos a la vez
    private static final AtomicInteger counter = new AtomicInteger(0); // recurso compartido

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            String name = "Worker-" + i;
            new Thread(() -> accessResource(name)).start();
        }
    }

    private static void accessResource(String name) {
        try {
            System.out.println(name + " quiere acceder al recurso...");

            semaphore.acquire(); // si hay 2 hilos dentro, espera
            System.out.println(name + " obtuvo acceso.");

            // Simula modificación del recurso compartido:
            int before = counter.get();
            Thread.sleep(500); // simula procesamiento
            int after = counter.incrementAndGet(); // ATÓMICO 🇨🇴

            System.out.println(name + " incrementó el contador: " + before + " → " + after);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // buena práctica

        } finally {
            System.out.println(name + " libera el recurso.");
            semaphore.release(); // SIEMPRE liberar
        }
    }
}
