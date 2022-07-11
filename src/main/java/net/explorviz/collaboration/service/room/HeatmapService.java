package net.explorviz.collaboration.service.room;

/**
 * A service that saves the state of the heatmap.
 */
public class HeatmapService {

  private boolean active;

  private String metric = "";

  private String mode = "";

  private String applicationId = "";

  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(final String metric) {
    this.metric = metric;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(final String mode) {
    this.mode = mode;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(final String applicationId) {
    this.applicationId = applicationId;
  }
}
