package de.flowpac.eta.domain;

public record Metric(String name, String description, double value) {

    public static Metric create(String name, String description, double value) {
        return new Metric(name, description, value);
    }

}
