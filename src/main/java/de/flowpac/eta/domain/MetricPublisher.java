package de.flowpac.eta.domain;

public interface MetricPublisher {

    void publishGauge(Metric metric);
}
