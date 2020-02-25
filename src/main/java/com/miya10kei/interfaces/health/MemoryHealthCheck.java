package com.miya10kei.interfaces.health;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class MemoryHealthCheck implements HealthCheck {
  private static long threshold = 1024000000;

  @Override
  public HealthCheckResponse call() {
    HealthCheckResponseBuilder responseBuilder =
        HealthCheckResponse.named("MemoryHealthCheck Liveness check");

    long freeMemory = Runtime.getRuntime().freeMemory();
    if (freeMemory >= threshold) {
      responseBuilder.up();
    } else {
      responseBuilder.down().withData("error", "No enough free memory! Please restart application");
    }

    return responseBuilder.build();
  }
}
