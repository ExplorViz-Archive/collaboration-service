package net.explorviz.collaboration.message.receivable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class HighlightingUpdateMessage extends ReceivableMessage {

  public static final String EVENT = "highlighting_update";

  private String appId;
  private String entityType;
  private String entityId;
  private boolean highlighted;
  private boolean multiSelected;

  public HighlightingUpdateMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  public String getEntityType() {
    return this.entityType;
  }

  public void setEntityType(final String entityType) {
    this.entityType = entityType;
  }

  public String getEntityId() {
    return this.entityId;
  }

  public void setEntityId(final String entityId) {
    this.entityId = entityId;
  }

  @JsonProperty("isHighlighted")
  public boolean isHighlighted() {
    return this.highlighted;
  }

  public void setIsHighlighted(final boolean isHighlighted) {
    this.highlighted = isHighlighted;
  }

  public void setIsMultiSelected(final boolean isMultiSelected) {
    this.multiSelected = isMultiSelected;
  }

  public boolean isMultiSelected() {
    return this.multiSelected;
  }

  @Override
  public String toString() {
    return "appId: " + this.appId + ", entityId: " + this.entityId + ", entityType: "
        + this.entityType + ", isHighlighted: " + this.highlighted + ", isMultiSelected: " 
        + this.multiSelected;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleHighlightingUpdateMessage(this, arg);
  }
}
