package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureModeMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_mode_update";

  public RestructureModeMessage() {
    super(EVENT);
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureModeMessage(this, arg);
  }
}
