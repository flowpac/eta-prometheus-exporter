package de.flowpac.eta.exporter.log;

import de.flowpac.eta.domain.Metric;
import de.flowpac.eta.domain.MetricPublisher;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;

@Dependent
public class LogPublisher implements MetricPublisher {
    @Override
    public void publishGauge(Metric metric) {
        Log.infof("%s[%s]: %s", metric.name(), metric.description(), metric.value());
    }
}
