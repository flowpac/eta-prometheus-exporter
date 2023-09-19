package de.flowpac.eta.adapter.eta;

import de.flowpac.eta.domain.Metric;
import de.flowpac.eta.domain.MetricClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Dependent
public class ETAClient implements MetricClient {
    public static final String BASE_URI = "http://192.168.2.142:8080";
    public static final String BASE_PATH = "/user/var";

    @Inject
    @ConfigProperty(name = "eta.url", defaultValue = BASE_URI)
    private String baseUrl;

    private double readValue(String path) {
        HttpClient client = HttpClient.newHttpClient();
        var uri = URI.create(baseUrl + BASE_PATH + path);
        Log.debugf("ETA API URL=%s", uri);
        var request = HttpRequest
                .newBuilder(uri)
                .timeout(Duration.of(5, ChronoUnit.SECONDS))
                .GET().build();
        try {
            var is = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            return Double.parseDouble(extractValueNode(is).getTextContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Node extractValueNode(HttpResponse<InputStream> is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is.body());
        return doc.getElementsByTagName("value").item(0);
    }

    @Override
    public List<Metric> readValues() {
        return List.of(
                create("total_pellets_consumption", "Total pellets consumption (kg).", "/40/10021/0/0/12016"),
                create("temperature_outside", "Outside temperature (°C).", "/120/10101/0/0/12197"),
                create("temperature_flow_heating_circuit", "Flow temperature heating circuit (°C)", "/120/10101/0/11060/0"),
                create("temperature_low_flow_heating_circuit", "Low (+10°C) flow temperature heating circuit (°C)", "/120/10101/0/0/12103"),
                create("temperature_high_flow_heating_circuit", "High (-10°C) flow temperature heating circuit (°C)", "/120/10101/0/0/12104"),
                create("heating_circuit_slider", "Heating circuit slider (%).", "/120/10101/0/0/12240"),
                create("temperature_water_current", "Current water temperature.", "/79/10531/0/0/12291"),
                create("temperature_water_target", "Target water temperature.", "/79/10531/0/0/12293"),
                create("temperature_water_flow", "Flow temperature water.", "/79/10531/0/0/12690"),
                create("sec_throughput", "Sec water throughput.", "/79/10531/12785/0/0"),
                create("ignition_counter", "Number of ignitions.", "/40/10021/0/0/12018", 1.0)
        );
    }

    private Metric create(String name, String description, String path, double scale) {
        return Metric.create(String.format("eta_%s", name), description, readValue(path) / scale);
    }

    private Metric create(String name, String description, String path) {
        return create(name, description, path, 10);
    }

}
