package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureCopyAndPasteClassMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_copy_paste_class";

  private String destinationId;
  private String clippedEntityId;

  public RestructureCopyAndPasteClassMessage() {
    super(EVENT);
  }

  public String getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(final String destinationId) {
    this.destinationId = destinationId;
  }

  public String getClippedEntityId() {
    return clippedEntityId;
  }

  public void setClippedEntityId(final String clippedEntityId) {
    this.clippedEntityId = clippedEntityId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureCopyAndPasteClassMessage(this, arg);
  }
}
