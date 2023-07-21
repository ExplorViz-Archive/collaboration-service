package net.explorviz.collaboration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PerformanceLogger {

  private static String DELAY_OF_PERF_LOGS = System.getenv("DELAY_OF_PERF_LOGS");
  private static String START_PERF_LOGS = System.getenv("START_PERF_LOGS");
  private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceLogger.class);

  // Path to the CPU limit and CPU period files
  private static final String cpuLimitFile = "/sys/fs/cgroup/cpu/cpu.cfs_quota_us";
  private static final String cpuPeriodFile = "/sys/fs/cgroup/cpu/cpu.cfs_period_us";

  // Path to the CPU usage file
  private static final String cpuUsageFile = "/sys/fs/cgroup/cpu/cpuacct.usage";

  // Store the initial CPU usage
  private long previousUsage = 0;

  private List<Double> cpuUsages = new ArrayList<>();
  private List<Double> memoryUsages = new ArrayList<>();

  public void logPerformance() {
    LOGGER.info("Performance will be logged");
    int delay = Integer.parseInt(DELAY_OF_PERF_LOGS);
    int initTime = Integer.parseInt(START_PERF_LOGS);

    sleep(initTime);

    new Thread(() -> {
      while (true) {
        sleep(delay);
        List<Double> lastCpuUsages = new ArrayList<>(cpuUsages);
        double averageCpuUsage = (lastCpuUsages.stream().mapToDouble(Double::doubleValue).sum()) / lastCpuUsages.size();

        List<Double> lastMemoryUsages = new ArrayList<>(memoryUsages);
        double averageMemoryUsage = (lastMemoryUsages.stream().mapToDouble(Double::doubleValue).sum())
                / lastMemoryUsages.size();

        if (LOGGER.isInfoEnabled()) {
          LOGGER.info("CPU usage: " + averageCpuUsage);
          LOGGER.info("RAM usage: " + averageMemoryUsage);
        }
      }
    }).start();

    previousUsage = readCpuUsage();

    while (true) {
      sleep(1000);

      double cpuUsage = getCpuUsage();
      cpuUsages.add(cpuUsage);

      double memoryUsage = getMemoryUsage();
      memoryUsages.add(memoryUsage);

    }
  }

  private void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private double getCpuUsage() {
    long currentUsage = readCpuUsage();
    if (currentUsage != -1) {
      double cpuUsagePercentage = calculateCpuUsagePercentage(previousUsage, currentUsage);
      previousUsage = currentUsage;
      return Math.round(cpuUsagePercentage * 100.0) / 100.0;
    }
    return -1;
  }

  // Calculate CPU usage percentage for the last second
  private double calculateCpuUsagePercentage(long previousUsage, long currentUsage) {
    double usageDelta = currentUsage - previousUsage;
    double cpuPeriodNanoSeconds = 1000000000; // 1 second = 1 billion nanoseconds
    int cpuCores = calculateNumberOfCpus(); // Number of CPU cores

    return (1.0 * usageDelta / (cpuPeriodNanoSeconds * cpuCores)) * 100.0;
  }

  private int calculateNumberOfCpus() {
    // Read and convert the CPU limit to number of CPUs
    int cpuLimit = readCpuLimit();
    int cpuPeriod = readCpuPeriod();
    if (cpuLimit != -1 && cpuPeriod != -1) {
      return cpuLimit / cpuPeriod;
    }
    return -1;
  }

  // Function to read the CPU limit from the file
  private int readCpuLimit() {
    try {
      return Integer.parseInt(new String(Files.readAllBytes(Paths.get(cpuLimitFile))).trim());
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
  }

  // Function to read the CPU period from the file
  private int readCpuPeriod() {
    try {
      return Integer.parseInt(new String(Files.readAllBytes(Paths.get(cpuPeriodFile))).trim());
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
  }

  // Function to read CPU usage from the file
  private long readCpuUsage() {
    try {
      return Long.parseLong(new String(Files.readAllBytes(Paths.get(cpuUsageFile))).trim());
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
  }

  private double getMemoryUsage() {
    String memStatFile = "/sys/fs/cgroup/memory/memory.usage_in_bytes";
    String totalMemoryFile = "/sys/fs/cgroup/memory/memory.limit_in_bytes";

    try {
      long memUsage = Long.parseLong(new String(Files.readAllBytes(Paths.get(memStatFile))).trim());
      long totalMemory = Long.parseLong(new String(Files.readAllBytes(Paths.get(totalMemoryFile))).trim());
      double memoryPercentage = 1.0 * memUsage / totalMemory * 100.0;

      return Math.round(memoryPercentage * 100.0) / 100.0;
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
  }

}