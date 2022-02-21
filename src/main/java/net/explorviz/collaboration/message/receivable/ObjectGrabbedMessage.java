package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessageHandler;
import net.explorviz.collaboration.message.RequestMessage;

public class ObjectGrabbedMessage extends RequestMessage {

  public static final String EVENT = "object_grabbed";

  private String objectId;

  public ObjectGrabbedMessage() {
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
    return handler.handleObjectGrabbedMessage(this, arg);
  }
}
