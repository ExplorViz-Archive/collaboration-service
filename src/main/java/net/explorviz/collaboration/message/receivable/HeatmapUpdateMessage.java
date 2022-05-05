package net.explorviz.collaboration.message.receivable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class HeatmapUpdateMessage extends ReceivableMessage {

  public static final String EVENT = "heatmap_update";

  private boolean active;
  private String applicationId;

  private String metric;

  private String mode;

  public HeatmapUpdateMessage() {
    super(EVENT);
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

  @JsonProperty("isActive")
  public boolean isActive() {
    return this.active;
  }

  public void setIsActive(final boolean isActive) {
    this.active = isActive;
  }

  public String getApplicationId() {
    return this.applicationId;
  }

  public void setApplicationId(final String applicationId) {
    this.applicationId = applicationId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleHeatmapUpdateMessage(this, arg);
  }
}
