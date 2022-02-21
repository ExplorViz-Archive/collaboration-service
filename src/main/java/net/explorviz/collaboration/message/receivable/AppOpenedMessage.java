package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class AppOpenedMessage extends ReceivableMessage {

  public static final String EVENT = "app_opened";

  private String id;
  private double[] position;
  private double[] quaternion;
  private double[] scale;

  public AppOpenedMessage() {
    super(EVENT);
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public double[] getPosition() {
    return this.position;
  }

  public void setPosition(final double ... position) {
    this.position = position;
  }

  public double[] getQuaternion() {
    return this.quaternion;
  }

  public void setQuaternion(final double ... quaternion) {
    this.quaternion = quaternion;
  }

  public double[] getScale() {
    return this.scale;
  }

  public void setScale(final double ... scale) {
    this.scale = scale;
  }

  @Override
  public String toString() {
    return "id: " + this.id;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleAppOpenedMessage(this, arg);
  }
}
