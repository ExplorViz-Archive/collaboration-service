package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ObjectReleasedMessage extends ReceivableMessage {

  public static final String EVENT = "object_released";

  private String objectId;

  public ObjectReleasedMessage() {
    super(EVENT);
  }

  public String getObjectId() {
    return this.objectId;
  }

  public void setObjectId(final String objectId) {
    this.objectId = objectId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleObjectReleasedMessage(this, arg);
  }
}
