package de.flowpac.eta.domain;

import java.util.List;

public interface MetricClient {

    List<Metric> readValues();
}
