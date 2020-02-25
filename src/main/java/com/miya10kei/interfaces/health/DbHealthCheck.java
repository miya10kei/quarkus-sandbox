package com.miya10kei.interfaces.health;

import java.io.IOException;
import java.net.Socket;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class DbHealthCheck implements HealthCheck {

  @ConfigProperty(name = "db.host")
  private String host;

  @ConfigProperty(name = "db.port")
  private Integer port;

  @Override
  public HealthCheckResponse call() {
    var responseBuild = HealthCheckResponse.named("Database connection health check");
    try {
      serverListening(host, port);
      responseBuild.up();
    } catch (Exception e) {
      responseBuild.down().withData("error", e.getMessage());
    }
    return responseBuild.build();
  }

  private void serverListening(String host, int port) throws IOException {
    var s = new Socket(host, port);
    s.close();
  }
}
