package Threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> work("Task-1", 1000));

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> work("Task-2", 1500));

        CompletableFuture<String> f3 =
                CompletableFuture.supplyAsync(() -> work("Task-3", 500));

        // Combinamos resultados de f1 y f2
        CompletableFuture<String> combinado12 =
                f1.thenCombine(f2, (r1, r2) -> "Combo[ " + r1 + " | " + r2 + " ]");

        // Ahora combinamos lo anterior con f3
        CompletableFuture<String> combinadoFinal =
                combinado12.thenCombine(f3, (r12, r3) -> r12 + " & " + r3);

        // Manejo de errores (si algo falla en la cadena)
        CompletableFuture<String> conFallback =
                combinadoFinal.exceptionally(e -> {
                    System.out.println("Ocurrió un error: " + e.getMessage());
                    return "VALOR_POR_DEFECTO";
                });

        // Esperamos SOLO el resultado final ya combinado
        String resultado = conFallback.get();
        System.out.println("Resultado final combinado: " + resultado);

        System.out.println("Flujo async completado.");
    }

    private static String work(String name, int millis) {
        System.out.println(name + " arrancó en " +
                Thread.currentThread().getName());
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " terminó.");
        return name + " OK";
    }

}
