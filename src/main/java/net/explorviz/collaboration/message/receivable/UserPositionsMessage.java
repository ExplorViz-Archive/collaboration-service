package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class UserPositionsMessage extends ReceivableMessage {

  public static final String EVENT = "user_positions";

  private ControllerPose controller1;
  private ControllerPose controller2;
  private Pose camera;

  public static class Pose {
    private double[] position;
    private double[] quaternion;

    public double[] getPosition() {
      return this.position.clone();
    }

    public void setPosition(final double[] position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion.clone();
    }
  }

  public static class ControllerPose extends Pose {
    private double[] intersection;

    public double[] getIntersection() {
      if(this.intersection != null)
        return this.intersection.clone();
      else // intersection might be null (even at runtime)
        return null;
    }

    public void setIntersection(final double[] intersection) {
      this.intersection = intersection.clone();
    }
  }

  public UserPositionsMessage() {
    super(EVENT);
  }

  public ControllerPose getController1() {
    return this.controller1;
  }

  public void setController1(final ControllerPose controller1) {
    this.controller1 = controller1;
  }

  public ControllerPose getController2() {
    return this.controller2;
  }

  public void setController2(final ControllerPose controller2) {
    this.controller2 = controller2;
  }

  public Pose getCamera() {
    return this.camera;
  }

  public void setCamera(final Pose camera) {
    this.camera = camera;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleUserPositionsMessage(this, arg);
  }
}
