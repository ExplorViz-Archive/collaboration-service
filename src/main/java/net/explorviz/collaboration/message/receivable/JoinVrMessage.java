package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class JoinVrMessage extends ReceivableMessage {
  public static final String EVENT = "join_vr";

  public JoinVrMessage() {
    super(EVENT);
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleJoinVrMessage(this, arg);
  }
}
