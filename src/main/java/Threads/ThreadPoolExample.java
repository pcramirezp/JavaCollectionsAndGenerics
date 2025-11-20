package Threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            System.out.println("=== 1) submit con Runnable (fire-and-forget) ===");
            executor.submit(() -> doWork("Runnable-1", 8000));
            executor.submit(() -> doWork("Runnable-2", 600));
            executor.submit(() -> doWork("Runnable-3", 400));

            System.out.println("\n=== 2) submit con Callable + Future + timeout ===");
            Callable<String> callableTask = () -> {
                doWork("Callable-Result", 1000);
                return "Resultado desde Callable";
            };

            Future<String> future = executor.submit(callableTask);

            try {
                // Esperamos máx 2 segundos por el resultado
                String result = future.get(2, TimeUnit.SECONDS);
                System.out.println("Future completado: " + result);
            } catch (TimeoutException e) {
                System.out.println("Timeout esperando el resultado del Callable");
            } catch (ExecutionException e) {
                System.out.println("Error dentro del Callable: " + e.getCause());
            }

            System.out.println("\n=== 3) Cancelar una tarea larga con Future.cancel ===");
            Callable<String> longTask = () -> {
                try {
                    for (int i = 1; i <= 5; i++) {
                        System.out.println("LongTask paso " + i + " en " + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    }
                    return "LongTask completada";
                } catch (InterruptedException e) {
                    System.out.println("LongTask interrumpida");
                    // Mantener el flag de interrupción
                    Thread.currentThread().interrupt();
                    return "LongTask cancelada por interrupción";
                }
            };

            Future<String> longFuture = executor.submit(longTask);

            Thread.sleep(1500); // dejamos que avance un poco
            System.out.println("Solicitando cancelación de LongTask...");
            boolean cancelled = longFuture.cancel(true); // true = intenta interrumpir

            System.out.println("¿Se canceló LongTask? " + cancelled);

            // Si intentas hacer get() después de cancel, lanza CancellationException
            try {
                longFuture.get();
            } catch (CancellationException e) {
                System.out.println("No se puede obtener resultado: la tarea fue cancelada.");
            } catch (ExecutionException e) {
                System.out.println("Error en LongTask: " + e.getCause());
            }

            System.out.println("\n=== 4) invokeAll con lote de Callables ===");
            List<Callable<String>> batch = Arrays.asList(
                    namedTask("Batch-1", 500),
                    namedTask("Batch-2", 700),
                    namedTask("Batch-3", 300)
            );

            // Ejecuta todas y devuelve una lista de Futures
            List<Future<String>> batchResults = executor.invokeAll(batch);

            for (Future<String> f : batchResults) {
                try {
                    System.out.println("Resultado batch: " + f.get());
                } catch (ExecutionException e) {
                    System.out.println("Error en tarea del batch: " + e.getCause());
                }
            }

        } finally {
            System.out.println("\n=== 5) shutdown + awaitTermination ===");
            // No aceptamos más tareas
            executor.shutdown();

            // Esperamos a que terminen las tareas actuales (máx 5 segundos)
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Todas las tareas han terminado correctamente.");
            } else {
                System.out.println("Aún quedan tareas ejecutándose, forzando shutdownNow()...");
                List<Runnable> pendientes = executor.shutdownNow();
                System.out.println("Tareas pendientes no iniciadas: " + pendientes.size());
            }
        }
    }

    // Simula trabajo de una tarea
    private static void doWork(String name, int millis) {
        System.out.println("▶ " + name + " ejecutándose en " + Thread.currentThread().getName());
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(name + " fue interrumpida.");
            Thread.currentThread().interrupt();
        }
        System.out.println("⏹ " + name + " finalizada.");
    }

    // Crea un Callable con nombre y duración
    private static Callable<String> namedTask(String name, int millis) {
        return () -> {
            doWork(name, millis);
            return name + " OK";
        };
    }
}
