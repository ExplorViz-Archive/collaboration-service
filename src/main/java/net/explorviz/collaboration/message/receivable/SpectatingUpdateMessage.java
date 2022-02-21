package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class SpectatingUpdateMessage extends ReceivableMessage {

  public static final String EVENT = "spectating_update";

  private String userId;
  private boolean spectating;
  private String spectatedUser;

  public SpectatingUpdateMessage() {
    super(EVENT);
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public boolean isSpectating() {
    return this.spectating;
  }

  public void setIsSpectating(final boolean isSpectating) {
    this.spectating = isSpectating;
  }

  public String getSpectatedUser() {
    return this.spectatedUser;
  }

  public void setSpectatedUser(final String spectatedUser) {
    this.spectatedUser = spectatedUser;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleSpectatingUpdateMessage(this, arg);
  }
}
