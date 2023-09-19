# ETA Pellets Heating Prometheus Exporter

A Prometheus exporter for *ETA Pellets Heating* metrics. Parses the XML response of the ETA API and transforms measures
into Prometheus metrics.

# Build

```
./mvnw clean package
```

# Quick Start

```
java -Dmeteo.latitude=46.7435 -Dmeteo.longitude=9.3355 -Deta.url=http://192.168.2.142:8080 -jar target/eta-prometheus-exporter-1.0-SNAPSHOT-runner.jar 
```

**Parameter**

- Open Meteo API (Outside Temperatures and Conditions)
    - meteo.latitude: Latitude Coordinates
    - meteo.longitude: Longitude Coordinates
- ETA Heating
    - eta.url: Base url of the ETA API (must be activated in advance).
- HTTP Server Parameters
    - [Quarkus](https://quarkus.io/guides/http-reference) HTTP Reference

# Access Prometheus Metrics
```
curl http://localhost:8080/metrics
```
# Docker
## Run Container
```
docker run -d --name eta-exporter -e METEO_LONGITUDE=9.3355 -e METEO_LATITUDE=46.7435 -p 8080:8080 flowpac/eta-prometheus-exporter:1.0.1
```

## Build Image
```
docker build . -t flowpac/eta-prometheus-exporter:tag
```
