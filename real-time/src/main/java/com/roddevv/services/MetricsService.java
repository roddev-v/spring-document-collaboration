package com.roddevv.services;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Component
public class MetricsService {
    private final AtomicInteger activeConnections = new AtomicInteger(0);

    @Autowired
    public MetricsService(MeterRegistry meterRegistry) {
        Supplier<Number> supplier = activeConnections::get;
        Gauge.builder("websocket_connections_total", supplier)
                .description("Number of active WebSocket connections")
                .register(meterRegistry);
    }

    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.decrementAndGet();
    }
}
