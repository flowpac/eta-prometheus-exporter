package de.flowpac.eta.exporter.prom;

import de.flowpac.eta.domain.Metric;
import de.flowpac.eta.domain.MetricPublisher;
import io.prometheus.client.Gauge;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PromPublisher implements MetricPublisher {

    private final Map<String, Gauge> gauges = new HashMap<>();

    public void publishGauge(Metric metric) {
        Gauge c = gauges.computeIfAbsent(metric.name(), x -> Gauge.build(metric.name(), metric.description()).register());
        c.set(metric.value());
    }


}
