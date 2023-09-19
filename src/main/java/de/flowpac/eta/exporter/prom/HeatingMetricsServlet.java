package de.flowpac.eta.exporter.prom;

import de.flowpac.eta.domain.MetricProcessor;
import io.prometheus.client.servlet.jakarta.exporter.MetricsServlet;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/metrics")
public class HeatingMetricsServlet extends MetricsServlet {

    @Inject
    MetricProcessor processor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processor.process();
        super.doGet(req, resp);
    }
}
