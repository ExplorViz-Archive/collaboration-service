package net.explorviz.collaboration.payload.sendable;

public class SynchronizationStartedMessage{

  private String roomId;

  public SynchronizationStartedMessage(final String roomId) {
    this.roomId = roomId;
  }

  public String getRoomId() {
    return this.roomId;
  }
  
}
