import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;

public class ConversorApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {

            System.out.println("=== Bienvenido al Conversor de Monedas ===");
            System.out.println("1. MXN (Peso Mexicano)");
            System.out.println("2. USD (Dólar Americano)");
            System.out.println("3. EUR (Euro)");
            System.out.println("4. BRL (Real Brasileño)");
            System.out.println("5. CLP (Peso Chileno)");
            System.out.println("6. COP (Peso Colombiano)");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();
            String monedaBase = null;

            switch (opcion) {
                case 1 -> monedaBase = "MXN";
                case 2 -> monedaBase = "USD";
                case 3 -> monedaBase = "EUR";
                case 4 -> monedaBase = "BRL";
                case 5 -> monedaBase = "CLP";
                case 6 -> monedaBase = "COP";
                case 7 -> {
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida, intenta de nuevo.");
            }

            if (monedaBase != null) {
                System.out.println("Elegiste convertir desde: " + monedaBase);

                // === Elegir moneda destino ===
                System.out.println("¿A qué moneda deseas convertir?");
                System.out.println("1. MXN");
                System.out.println("2. USD");
                System.out.println("3. EUR");
                System.out.println("4. BRL");
                System.out.println("5. CLP");
                System.out.println("6. COP");
                System.out.print("Elige la moneda destino: ");

                int opcDestino = scanner.nextInt();
                String monedaDestino = null;

                switch (opcDestino) {
                    case 1 -> monedaDestino = "MXN";
                    case 2 -> monedaDestino = "USD";
                    case 3 -> monedaDestino = "EUR";
                    case 4 -> monedaDestino = "BRL";
                    case 5 -> monedaDestino = "CLP";
                    case 6 -> monedaDestino = "COP";
                    default -> System.out.println("Opción inválida.");
                }

                if (monedaDestino != null) {
                    // === Pedir cantidad ===
                    System.out.print("¿Cantidad a convertir? ");
                    double cantidad = scanner.nextDouble();

                    System.out.println("Convirtiendo " + cantidad + " " + monedaBase + " → " + monedaDestino);

                    // === Llamada a la API ===
                    try {
                        String apiKey = "b9b3d575572b0abb13b386f4";

                        String url = "https://v6.exchangerate-api.com/v6/"
                                + apiKey + "/pair/"
                                + monedaBase + "/"
                                + monedaDestino;

                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .build();

                        HttpResponse<String> response = client.send(
                                request,
                                HttpResponse.BodyHandlers.ofString()
                        );

                        Gson gson = new Gson();
                        ExchangeResponse data = gson.fromJson(response.body(), ExchangeResponse.class);

                        double tasa = data.conversion_rate;
                        double resultado = cantidad * tasa;

                        System.out.println("Tasa actual: 1 " + monedaBase + " = " + tasa + " " + monedaDestino);
                        System.out.println("Resultado final: " + resultado + " " + monedaDestino);

                    } catch (Exception e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                }
                // Espacio entre ciclos
                System.out.println();
            }
        }

        scanner.close();
    }
}
