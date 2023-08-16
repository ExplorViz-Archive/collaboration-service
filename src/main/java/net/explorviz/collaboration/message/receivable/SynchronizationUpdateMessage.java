package net.explorviz.collaboration.message.receivable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class SynchronizationUpdateMessage extends ReceivableMessage {
  public static final String EVENT = "synchronization_update";

  private String userId;
  private boolean synchronizing;
  private String main;

  public SynchronizationUpdateMessage() {
    super(EVENT);
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  @JsonProperty("isSynchronizing")
  public boolean isSynchronizing() {
    return this.synchronizing;
  }

  public void setIsSynchronzing(final boolean isSynchronizing) {
    this.synchronizing = isSynchronizing;
  }

  public String getMain() {
    return this.main;
  }

  public void setMain(final String main) {
    this.main = main;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleSynchronizationUpdateMessage(this, arg);
  }
}
