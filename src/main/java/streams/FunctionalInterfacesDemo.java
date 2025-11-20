package streams;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionalInterfacesDemo {

    public static void main(String[] args) {
        processNames();  // main limpio, fácil de testear
    }


    // Flujo principal (solo orquesta)
    public static void processNames() {
        // a partir de dos streams...
        Stream<String> namesStream = namesSupplier.get();
        Stream<String> lastNamesStream = lastNamesSupplier.get();

        // ...los "zippeamos" en un único stream de "Nombre Apellido"
        Stream<String> fullNames = zipSafe(
                namesStream,
                lastNamesStream,
                (optName, optLast) -> concatFullName().apply(
                        optName.orElse("SIN_NOMBRE"),
                        optLast.orElse("SIN_APELLIDO")
                )
        );

        fullNames
                // 3) invertimos → "Apellido Nombre"
                .map(formatNameFn())
                // 4) excluimos cierto apellido
                .filter(excludeLastName("Gómez"))
                // 5) excluimos si tienen menos de n caracteres
                .filter(minLength(10))
                // ordenar para dejarlo bonito
                .sorted()
                // 7) imprimir en consola
                .forEach(printConsumer());

        // 8) mensaje final
        onFinish().run();
    }

    // Utilidad: "zip" de dos streams en uno solo, usando un combinador (BiFunction)
    public static <A, B, R> Stream<R> zipSafe(
            Stream<A> s1,
            Stream<B> s2,
            BiFunction<Optional<A>, Optional<B>, R> combiner) {

        Iterator<A> it1 = s1.iterator();
        Iterator<B> it2 = s2.iterator();

        return Stream.generate(() -> new Pair<>(
                        it1.hasNext() ? Optional.of(it1.next()) : Optional.empty(),
                        it2.hasNext() ? Optional.of(it2.next()) : Optional.empty()
                ))
                .takeWhile(p -> p.a.isPresent() || p.b.isPresent())  // ¡Ahora sí compila!
                .map(p -> combiner.apply(p.a, p.b));
    }

    public static class Pair<A, B> {
        final Optional<A> a;
        final Optional<B> b;

        Pair(Optional<A> a, Optional<B> b) {
            this.a = a;
            this.b = b;
        }
    }

    // SUPPLIER 1 → Nombres
    public static Supplier<Stream<String>> namesSupplier = () ->
            Stream.of("Juan", "Ana", "Carlos", "Laura", "Ren");

    // SUPPLIER 2 → Apellidos
    public static Supplier<Stream<String>> lastNamesSupplier = () ->
            Stream.of("Pérez", "Gómez", "Ruiz", "Martínez");

    // BinaryOperator: "nombre", "apellido" -> "Apellido Nombre"
    static BinaryOperator<String> concatFullName() {
        return (firstName, lastName) -> lastName + " " + firstName;
    }

    // -----------------------------------------------------------------------
    // FUNCTION → Formatea nombre: "Nombre Apellido" → "Apellido, Nombre"
    // -----------------------------------------------------------------------
    public static UnaryOperator<String> formatNameFn() {
        return fullName -> {
            String[] parts = fullName.split(" ");
            return parts[1] + " " + parts[0];
        };
    }

    // -----------------------------------------------------------------------
    // PREDICATE → Excluir un apellido dinámicamente
    // -----------------------------------------------------------------------
    public static Predicate<String> excludeLastName(String lastName) {
        return formattedName -> !formattedName.startsWith(lastName);
    }

    // Otro Predicate: solo nombres largos
    public static Predicate<String> minLength(Integer length) {
        return name -> name.length() > length;
    }

    // -----------------------------------------------------------------------
    // CONSUMER → Acción con cada elemento
    // -----------------------------------------------------------------------
    public static Consumer<String> printConsumer() {
        return formatted -> System.out.println("→ " + formatted);
    }

    // -----------------------------------------------------------------------
    // RUNNABLE → Acción final (no recibe ni devuelve)
    // -----------------------------------------------------------------------
    public static Runnable onFinish() {
        return () -> System.out.println("Stream finalizado");
    }

}

