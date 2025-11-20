package Threads;

public class SyncCounterExample {


    private static final Counter counter1 = new Counter("Counter-1");
    private static final Counter counter2 = new Counter("Counter-2");

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Escenario 1: Deadlock forzado ===");
        runDeadlockScenario();

        System.out.println("\n\n=== Escenario 2: Versión segura sin deadlock ===");
        runSafeScenario();
    }

    // 1) DEADLOCK + detección sin colgarse
    private static void runDeadlockScenario() throws InterruptedException {
        Runnable taskAB = () -> {
            for (int i = 0; i < 1000; i++) {
                incrementAB(); // lock en counter1 -> counter2
            }
        };

        Runnable taskBA = () -> {
            for (int i = 0; i < 1000; i++) {
                incrementBA(); // lock en counter2 -> counter1
            }
        };

        Thread t1 = new Thread(taskAB, "T-AB");
        Thread t2 = new Thread(taskBA, "T-BA");

        // Marca como daemon: si main termina, la JVM puede cerrar aunque estos sigan vivos
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        // Esperamos un rato razonable
        t1.join(2000);
        t2.join(2000);

        if (t1.isAlive() && t2.isAlive()) {
            System.out.println("⚠ Posible DEADLOCK detectado:");
            System.out.println("  " + t1.getName() + " estado: " + t1.getState());
            System.out.println("  " + t2.getName() + " estado: " + t2.getState());
            System.out.println("  Ambos siguen vivos tras el timeout.\n");
        } else {
            System.out.println("No hubo deadlock en este run.");
        }

        System.out.println("Terminando escenario 1 (los hilos quedan como daemon).");
        System.out.println("Counter-1: " + counter1.getValue());
        System.out.println("Counter-2: " + counter2.getValue());
    }

    private static void incrementAB() {
        synchronized (counter1) {
            sleep(1);
            synchronized (counter2) {
                counter1.incrementUnsafe();
                counter2.incrementUnsafe();
            }
        }
    }

    private static void incrementBA() {
        synchronized (counter2) {
            sleep(1);
            synchronized (counter1) {
                counter1.incrementUnsafe();
                counter2.incrementUnsafe();
            }
        }
    }

    // 2) Versión segura sin deadlock usando lock ordering
    private static void runSafeScenario() throws InterruptedException {
        counter1.reset();
        counter2.reset();

        Runnable task1 = () -> {
            for (int i = 0; i < 1000; i++) {
                safeIncrement(counter1, counter2);
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 1000; i++) {
                safeIncrement(counter2, counter1); // el orden de parámetros da igual
            }
        };

        Thread s1 = new Thread(task1, "Safe-1");
        Thread s2 = new Thread(task2, "Safe-2");

        s1.start();
        s2.start();

        if (s1.isAlive() && s2.isAlive()) {
            System.out.println("⚠ Posible DEADLOCK detectado:");
            System.out.println("  " + s1.getName() + " estado: " + s1.getState());
            System.out.println("  " + s2.getName() + " estado: " + s2.getState());
            System.out.println("  Ambos siguen vivos tras el timeout.\n");
        } else {
            System.out.println("No hubo deadlock en este run.");
        }

        s1.join();
        s2.join();

        System.out.println("✔ Versión segura terminada sin deadlock.");
        System.out.println("Counter-1: " + counter1.getValue());
        System.out.println("Counter-2: " + counter2.getValue());
    }

    // Lock ordering: siempre se adquieren en el mismo orden global
    private static void safeIncrement(Counter c1, Counter c2) {
        Counter first;
        Counter second;

        if (System.identityHashCode(c1) < System.identityHashCode(c2)) {
            first = c1;
            second = c2;
        } else {
            first = c2;
            second = c1;
        }

        synchronized (first) {
            synchronized (second) {
                c1.incrementUnsafe();
                c2.incrementUnsafe();
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class Counter {
        private int value = 0;
        private final String name;

        Counter(String name) {
            this.name = name;
        }

        public void incrementUnsafe() {
            value++;
        }

        public int getValue() {
            return value;
        }

        public void reset() {
            value = 0;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
