package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class UserControllerDisconnectMessage extends ReceivableMessage {
  public static final String EVENT = "user_controller_disconnect";

  private int controllerId;

  public UserControllerDisconnectMessage() {
    super(EVENT);
  }

  public int getControllerId() {
    return this.controllerId;
  }

  public void setControllerId(final int controllerId) {
    this.controllerId = controllerId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleUserControllerDisconnectMessage(this, arg);
  }
}
