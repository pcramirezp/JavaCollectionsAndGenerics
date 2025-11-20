package Threads;

public class VolatileStopExample {

    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> work("Worker-1"));
        Thread t2 = new Thread(() -> work("Worker-2"));
        Thread t3 = new Thread(() -> work("Worker-3"));

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000); // los dejamos trabajar un rato

        System.out.println("Main: solicitando apagado...");
        running = false; // cambio visible a todos

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Todos se detuvieron.");
    }

    private static void work(String name) {
        while (running) {
            System.out.println(name + " trabajando...");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(name + " apagado limpio.");
    }

}
