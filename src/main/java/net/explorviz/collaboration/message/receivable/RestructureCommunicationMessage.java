package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureCommunicationMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_communication";

  private String sourceClassId;
  private String targetClassId;
  private String methodName;

  public RestructureCommunicationMessage() {
    super(EVENT);
  }

  public String getSourceClassId() {
    return sourceClassId;
  }

  public void setSourceClassId(final String sourceClassId) {
    this.sourceClassId = sourceClassId;
  }

  public String getTargetClassId() {
    return targetClassId;
  }

  public void setTargetClassId(final String targetClassId) {
    this.targetClassId = targetClassId;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(final String methodName) {
    this.methodName = methodName;
  }

  @Override
  public String toString() {
    return "sourceClassId: " + this.sourceClassId + ", targetClassId: "
        + this.targetClassId + ", methodName: " + this.methodName;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureCommunicationMessage(this, arg);
  }
}