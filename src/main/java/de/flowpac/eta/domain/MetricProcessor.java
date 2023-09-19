package de.flowpac.eta.domain;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@Dependent
public class MetricProcessor {

    private final Instance<MetricPublisher> publishers;
    private final Instance<MetricClient> clients;

    @Inject
    public MetricProcessor(Instance<MetricPublisher> publishers, Instance<MetricClient> clients) {
        this.publishers = publishers;
        this.clients = clients;
    }

    public void process() {
        for (MetricClient client : clients) {
            try {
                client.readValues().forEach(this::publishGauge);
            } catch (RuntimeException e) {
                Log.error("Could not process metrics.", e);
            }
        }
    }

    private void publishGauge(Metric metric) {
        publishers.forEach(p -> p.publishGauge(metric));
    }

}
