package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class PingUpdateMessage extends ReceivableMessage {

  public PingUpdateMessage() {
    super(EVENT);
  }

  public static final String EVENT = "ping_update";

  private int controllerId;

  private boolean pinging;

  public int getControllerId() {
    return this.controllerId;
  }

  public void setControllerId(final int controllerId) {
    this.controllerId = controllerId;
  }

  public boolean isPinging() {
    return this.pinging;
  }

  public void setPinging(final boolean isPinging) {
    this.pinging = isPinging;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handlePingUpdateMessage(this, arg);
  }
}
