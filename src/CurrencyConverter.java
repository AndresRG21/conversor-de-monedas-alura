import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {
    private static final String API_KEY = "e589aad11da9865dae44f508";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private String baseCurrency;

    public CurrencyConverter(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        try {
            // Construir la URL para la solicitud de API
            String endpoint = BASE_URL + API_KEY + "/latest/" + fromCurrency;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Verificar si la conexión fue exitosa
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Leer la respuesta de la API
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parsear la respuesta JSON con Gson
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

                // Obtener la tasa de conversión
                if (conversionRates.has(toCurrency)) {
                    double rate = conversionRates.get(toCurrency).getAsDouble();
                    return amount * rate;
                } else {
                    System.out.println("La moneda de destino no está disponible.");
                }
            } else {
                System.out.println("Error en la solicitud: Código " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}