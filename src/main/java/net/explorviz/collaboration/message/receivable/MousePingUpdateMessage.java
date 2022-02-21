package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class MousePingUpdateMessage extends ReceivableMessage {

  public MousePingUpdateMessage() {
    super(EVENT);
  }

  public static final String EVENT = "mouse_ping_update";

  private String modelId;
  private boolean isApp;
  private double[] position;

  public String getModelId() {
    return this.modelId;
  }

  public void setModelId(final String modelId) {
    this.modelId = modelId;
  }

  public boolean isApplication() {
    return this.isApp;
  }

  public void setIsApplication(final boolean isApplication) {
    this.isApp = isApplication;
  }

  public double[] getPosition() {
    return this.position;
  }

  public void setPosition() {
    this.position = this.position;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleMousePingUpdateMessage(this, arg);
  }
}
