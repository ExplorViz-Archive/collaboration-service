package net.explorviz.collaboration.message.receivable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ComponentUpdateMessage extends ReceivableMessage {

  public static final String EVENT = "component_update";

  private String appId;
  private String componentId;
  private boolean opened;
  private boolean foundation;
  private boolean forward;

  public ComponentUpdateMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  public String getComponentId() {
    return this.componentId;
  }

  public void setComponentId(final String componentId) {
    this.componentId = componentId;
  }

  @JsonProperty("isOpened")
  public boolean isOpened() {
    return this.opened;
  }

  public void setIsOpened(final boolean isOpened) {
    this.opened = isOpened;
  }

  @JsonProperty("isFoundation")
  public boolean isFoundation() {
    return this.foundation;
  }

  public void setIsFoundation(final boolean isFoundation) {
    this.foundation = isFoundation;
  }

  public boolean isForward() {
    return this.forward;
  }

  @Override
  public String toString() {
    return "appId: " + this.appId + ", componentId: " + this.componentId + ", isOpened:"
        + this.opened + ", isFoundation: " + this.foundation;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleComponentUpdateMessage(this, arg);
  }
}
