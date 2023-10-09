package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class AllHighlightsResetMessage extends ReceivableMessage {

  public static final String EVENT = "all_highlights_reset";

  
  public AllHighlightsResetMessage() {
    super(EVENT);
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleAllHighlightsResetMessage(this, arg);
  }
}
