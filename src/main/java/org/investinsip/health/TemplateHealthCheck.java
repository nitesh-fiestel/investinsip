package org.investinsip.health;

import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    @Override
    protected Result check() {
        // Simple health check that always returns healthy
        // In a real application, you would check the health of your dependencies here
        return Result.healthy("Application is healthy");
    }
}
