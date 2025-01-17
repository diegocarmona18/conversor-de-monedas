
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorApp {

    // Method to fetch exchange rates using a free API (exchangerate.host)
    public static double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String apiUrl = String.format("https://api.exchangerate.host/convert?from=%s&to=%s", fromCurrency, toCurrency);
        
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
            scanner.close();
            
            JSONObject response = new JSONObject(jsonResponse.toString());
            return response.getDouble("result");
        } else {
            throw new IOException("Failed to fetch exchange rates. HTTP response code: " + responseCode);
        }
    }

    // Main method to interact with the user
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Conversor de Monedas!");
        System.out.print("Ingrese el monto en dólares (USD): ");
        double amountInDollars = scanner.nextDouble();

        try {
            double exchangeRate = getExchangeRate("USD", "CRC");
            double amountInColones = amountInDollars * exchangeRate;
            
            System.out.printf("El monto en colones costarricenses (CRC) es: %.2f\n", amountInColones);
        } catch (IOException e) {
            System.out.println("Hubo un error al obtener las tasas de cambio: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
