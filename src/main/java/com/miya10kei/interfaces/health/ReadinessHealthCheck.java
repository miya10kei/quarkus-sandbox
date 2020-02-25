package com.miya10kei.interfaces.health;

import java.nio.file.Files;
import java.nio.file.Paths;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {
  @Override
  public HealthCheckResponse call() {
    var responseBuilder = HealthCheckResponse.named("File System Readiness check");
    var tempFileExists = Files.exists(Paths.get("/tmp/tmp.lck"));
    if (!tempFileExists) {
      responseBuilder.up();
    } else {
      responseBuilder.down().withData("error", "Lock file detected!");
    }
    return responseBuilder.build();
  }
}
