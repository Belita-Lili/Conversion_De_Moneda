import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        String amountString = JOptionPane.showInputDialog("Ingrese la cantidad a convertir: ");
        double amount = 0.0;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: La cantidad ingresada no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fromCurrency = JOptionPane.showInputDialog("Ingrese la moneda de origen (por ejemplo, USD): ");
        String toCurrency = JOptionPane.showInputDialog("Ingrese la moneda de destino (por ejemplo, EUR): ");

        String fromCurrency1 = fromCurrency.toUpperCase();
        String toCurrency1 = toCurrency.toUpperCase();

        try {
            String jsonResponse = getJsonResponse(API_URL + fromCurrency1);
            double convertedAmount = convertCurrency(amount, jsonResponse, toCurrency1);
            String resultMessage = amountString + " " + fromCurrency1 + " = " + convertedAmount + " " + toCurrency1;
            JOptionPane.showMessageDialog(null, resultMessage, "Resultado de la conversión", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al realizar la conversión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String getJsonResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } else {
            throw new IOException("Error al obtener la respuesta del servidor. Código de estado: " + responseCode);
        }
    }

    private static double convertCurrency(double amount, String jsonResponse, String toCurrency) {
        // Analizar la respuesta JSON y obtener la tasa de cambio de la moneda de destino
        // Aquí se asume que la respuesta JSON tiene el formato proporcionado en la pregunta
        // Se podría utilizar una librería de parsing JSON como Gson o Jackson para un análisis más robusto
        // Pero para este ejemplo, se realizará un análisis simple de la cadena JSON
        String[] parts = jsonResponse.split("\"" + toCurrency + "\":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("La moneda de destino no fue encontrada en la respuesta JSON");
        }

        String ratePart = parts[1].split(",")[0];
        return amount * Double.parseDouble(ratePart);
    }
}