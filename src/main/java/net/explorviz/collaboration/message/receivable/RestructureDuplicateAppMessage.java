package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureDuplicateAppMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_duplicate_app";

  private String appId;

  public RestructureDuplicateAppMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureDuplicateAppMessage(this, arg);
  }
}
