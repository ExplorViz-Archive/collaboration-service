package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessageHandler;
import net.explorviz.collaboration.message.RequestMessage;

public class AppClosedMessage extends RequestMessage {

  public static final String EVENT = "app_closed";

  private String appId;

  public AppClosedMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  @Override
  public String toString() {
    return "appId: " + this.appId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleAppClosedMessage(this, arg);
  }
}
