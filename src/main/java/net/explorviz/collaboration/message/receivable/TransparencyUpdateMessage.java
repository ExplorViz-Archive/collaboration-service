package net.explorviz.collaboration.message.receivable;


import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class TransparencyUpdateMessage extends ReceivableMessage {
public static final String EVENT = "transparency_update";

  private String appId;
  private String[] entityIds;

  public TransparencyUpdateMessage() {
    super(EVENT);
  }

  public String getAppId() {
    return this.appId;
  }

  public void setAppId(final String appId) {
    this.appId = appId;
  }

  public String[] getEntityIds() {
    return this.entityIds;
  }

  public void setEntityIds(final String[] entityIds) {
    this.entityIds = entityIds.clone();
  }
  @Override
  public String toString() {
    return "appId: " + this.appId + ", entityIds: " + this.entityIds;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleTransparencyUpdateMessage(this, arg);
  }
}

