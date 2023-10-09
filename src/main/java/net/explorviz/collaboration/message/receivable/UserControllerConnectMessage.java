package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class UserControllerConnectMessage extends ReceivableMessage {
  public static final String EVENT = "user_controller_connect";

  private Controller controller;


  public UserControllerConnectMessage() {
    super(EVENT);
  }

  public Controller getController() {
    return this.controller;
  }

  public void setController(final Controller controller) {
    this.controller = controller;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleUserControllerConnectMessage(this, arg);
  }


  public static class Controller {
    private int controllerId;
    private String assetUrl;
    private double[] position;
    private double[] quaternion;
    private double[] intersection;

    public int getControllerId() {
      return this.controllerId;
    }

    public void setControllerId(final int controllerId) {
      this.controllerId = controllerId;
    }

    public String getAssetUrl() {
      return this.assetUrl;
    }

    public void setAssetUrl(final String assetUrl) {
      this.assetUrl = assetUrl;
    }

    public double[] getPosition() {
      return this.position.clone();
    }

    public void setPosition(final double... position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion.clone();
    }

    public double[] getIntersection() {
      if (this.intersection != null) { // NOPMD
        return this.intersection.clone();
      } else {
        return null;
      }
    }

    public void setIntersection(final double[] intersection) {
      this.intersection = intersection.clone();
    }
  }
}
