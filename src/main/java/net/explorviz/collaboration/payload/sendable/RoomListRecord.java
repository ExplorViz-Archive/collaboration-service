package net.explorviz.collaboration.payload.sendable;

public class RoomListRecord {
  private final String roomId;
  private final String roomName;

  public RoomListRecord(final String roomId, final String roomName) {
    super();
    this.roomId = roomId;
    this.roomName = roomName;
  }

  public String getRoomId() {
    return this.roomId;
  }

  public String getRoomName() {
    return this.roomName;
  }
}
