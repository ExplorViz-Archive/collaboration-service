package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ObjectMovedMessage extends ReceivableMessage {

  public static final String EVENT = "object_moved";

  private String objectId;
  private double[] position;
  private double[] quaternion;
  private double[] scale;

  public ObjectMovedMessage() {
    super(EVENT);
  }

  public String getObjectId() {
    return this.objectId;
  }

  public void setObjectId(final String objectId) {
    this.objectId = objectId;
  }

  public double[] getPosition() {
    return this.position;
  }

  public void setPosition(final double[] position) {
    this.position = position;
  }

  public double[] getQuaternion() {
    return this.quaternion;
  }

  public void setQuaternion(final double[] quaternion) {
    this.quaternion = quaternion;
  }

  public double[] getScale() {
    return this.scale;
  }

  public void setScale(final double[] scale) {
    this.scale = scale;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleObjectMovedMessage(this, arg);
  }
}
