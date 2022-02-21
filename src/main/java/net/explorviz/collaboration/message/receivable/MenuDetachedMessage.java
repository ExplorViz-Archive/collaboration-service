package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessageHandler;
import net.explorviz.collaboration.message.RequestMessage;

public class MenuDetachedMessage extends RequestMessage {

  public static final String EVENT = "menu_detached";

  private String detachId;
  private String entityType;
  private double[] position;
  private double[] quaternion;
  private double[] scale;

  public MenuDetachedMessage() {
    super(EVENT);
  }

  public String getDetachId() {
    return this.detachId;
  }

  public String getEntityType() {
    return this.entityType;
  }

  public double[] getPosition() {
    return this.position.clone();
  }

  public double[] getQuaternion() {
    return this.quaternion.clone();
  }

  public void setDetachId(final String detachId) {
    this.detachId = detachId;
  }

  public void setEntityType(final String entityType) {
    this.entityType = entityType;
  }

  public void setPosition(final double ... position) {
    this.position = position.clone();
  }

  public void setQuaternion(final double ... quaternion) {
    this.quaternion = quaternion.clone();
  }

  public double[] getScale() {
    return this.scale.clone();
  }

  public void setScale(final double ... scale) {
    this.scale = scale.clone();
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleMenuDetachedMessage(this, arg);
  }

}
