package net.explorviz.collaboration;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.lang.management.ManagementFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public final class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static String logPerf = System.getenv("LOG_PERF");

  private static String delayOfPerfLogs = System.getenv("DELAY_OF_PERF_LOGS");

  private Main() {
  }

  public static void main(final String... args) {
    if ("true".equals(logPerf)) {
      logPerformance();
    }
    Quarkus.run(args);
  }

  private static void logPerformance() {
    LOGGER.info("Performance will be logged");
    final int delay = Integer.parseInt(delayOfPerfLogs);
    final var bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
            .getOperatingSystemMXBean();
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(delay);
          if (LOGGER.isInfoEnabled()) {
            LOGGER.info("JVM process CPU usage: " + bean.getProcessCpuLoad());
          }
        } catch (InterruptedException e) {
          LOGGER.info("Could not log performance");
        }
      }
    }).start();
  }
}