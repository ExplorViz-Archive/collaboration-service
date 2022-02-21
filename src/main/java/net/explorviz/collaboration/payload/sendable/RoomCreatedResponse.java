package net.explorviz.collaboration.payload.sendable;

public class RoomCreatedResponse {
  private final String roomId;

  public RoomCreatedResponse(final String roomId) {
    this.roomId = roomId;
  }

  public String getRoomId() {
    return this.roomId;
  }
}
