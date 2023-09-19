package de.flowpac.eta.adapter.meteo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowpac.eta.domain.Metric;
import de.flowpac.eta.domain.MetricClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Dependent
public class OpenMeteoClient implements MetricClient {

    private static final String BASE_URL = "https://api.open-meteo.com";
    private static final String BASE_PATH = "/v1/forecast";

    @Inject
    @ConfigProperty(name = "meteo.longitude", defaultValue = "0")
    private double longitude;

    @Inject
    @ConfigProperty(name = "meteo.latitude", defaultValue = "0")
    private double latitude;


    @SneakyThrows
    private CurrentWeatherDto read() {
        var client = HttpClient.newHttpClient();
        var queryString = String.format("?latitude=%s&longitude=%s&current_weather=true", latitude, longitude);
        Log.debugf("Open Meteo API URL: %s", BASE_URL + BASE_PATH + queryString);
        var request = HttpRequest.newBuilder().GET().uri(URI.create(BASE_URL + BASE_PATH + queryString)).build();
        var result = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var w = objectMapper.readValue(result.body(), WeatherDto.class);
        return w.getCurrentWeather();
    }

    @Override
    public List<Metric> readValues() {
        if (longitude == 0 || latitude == 0) {
            return List.of();
        }

        var weather = read();
        return List.of(
                create("temperature", "temperature at given location.", weather.getTemperature()),
                create("windspeed", "windspeed at given location.", weather.getWindSpeed()),
                create("winddirection", "winddirection at given location.", weather.getWindDirection()),
                create("code", "weather code at given location.", weather.getWeatherCode())
        );
    }

    private Metric create(String name, String description, double value) {
        return Metric.create(String.format("open_meteo_%s", name), description, value);
    }
}
