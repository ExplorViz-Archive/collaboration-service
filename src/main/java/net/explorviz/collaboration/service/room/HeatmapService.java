package net.explorviz.collaboration.service.room;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A service that saves the state of the heatmap.
 */
public class HeatmapService {

  private boolean active = false;

  private String metric = "";

  private String mode = "";

  private String applicationId = "";

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }
}
