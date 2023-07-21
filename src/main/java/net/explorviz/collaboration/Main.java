package net.explorviz.collaboration;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.lang.management.ManagementFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public final class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private Main() {
  }

  public static void main(final String... args) {
    var perfLogger = new PerformanceLogger();
    new Thread(() -> {
      perfLogger.logPerformance();
    }).start();
    Quarkus.run(args);
  }
}