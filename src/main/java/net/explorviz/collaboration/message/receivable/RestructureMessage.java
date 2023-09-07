package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_update";

  private String entityType;
  private String entityId;
  private String newName;
  private String appId;
  private boolean undo;

  public RestructureMessage() {
    super(EVENT);
  }

  public String getEntityType() {
    return this.entityType;
  }

  public void setEntityType(final String entityType) {
    this.entityType = entityType;
  }

  public String getEntityId() {
    return this.entityId;
  }

  public void setEntityId(final String entityId) {
    this.entityId = entityId;
  }

  public String getNewName() {
    return this.newName;
  }

  public void setNewName(final String newName) {
    this.newName = newName;
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  public boolean getUndo() {
    return this.undo;
  }

  public void setUndo(final boolean undo) {
    this.undo = undo; 
  }

  @Override
  public String toString() {
    return "entityId: " + this.entityId + ", entityType: "
        + this.entityType + ", newName: " + this.newName;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureMessage(this, arg);
  }
}
