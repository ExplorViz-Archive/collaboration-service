package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureCreateOrDeleteMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_create_delete";

  private String action;
  private String entityType;
  private String name;
  private String language;
  private String entityId;
  private boolean undo;

  public RestructureCreateOrDeleteMessage() {
    super(EVENT);
  }

  public String getAction() {
    return this.action;
  }

  public void setAction(final String action) {
    this.action = action;
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

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(final String language) {
    this.language = language;
  }

  public boolean isUndo() {
    return this.undo;
  }

  public void setUndo(final boolean undo) {
    this.undo = undo;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureCreateOrDeleteMessage(this, arg);
  }
}
