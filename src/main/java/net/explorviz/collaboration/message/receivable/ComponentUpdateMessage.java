package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ComponentUpdateMessage extends ReceivableMessage {

  public static final String EVENT = "component_update";

  private String appId;
  private String componentId;
  private boolean opened;
  private boolean isFoundationComponent;

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

  public boolean isOpened() {
    return this.opened;
  }

  public void setIsOpened(final boolean isOpened) {
    this.opened = isOpened;
  }

  public boolean isFoundation() {
    return this.isFoundationComponent;
  }

  public void setIsFoundation(final boolean isFoundation) {
    this.isFoundationComponent = isFoundation;
  }

  @Override
  public String toString() {
    return "appId: " + this.appId + ", componentId: " + this.componentId + ", isOpened:"
        + this.opened + ", isFoundation: " + this.isFoundationComponent;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleComponentUpdateMessage(this, arg);
  }
}
