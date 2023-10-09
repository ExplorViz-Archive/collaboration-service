package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureRestoreAppMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_restore_app";

  private String appId;
  private boolean undoCutOperation;

  public RestructureRestoreAppMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  public boolean isUndoCutOperation() {
    return undoCutOperation;
  }

  public void setUndoCutOperation(final boolean undoCutOperation) {
    this.undoCutOperation = undoCutOperation;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureRestoreAppMessage(this, arg);
  }
}
