package net.explorviz.collaboration.payload.sendable;

public class RoomListRecord {
  private final String roomId;
  private final String roomName;
  private final String landscapeToken;

  private final int size;

  public RoomListRecord(final String roomId, final String roomName, final String landscapeToken,
      final int size) {
    super();
    this.roomId = roomId;
    this.roomName = roomName;
    this.landscapeToken = landscapeToken;
    this.size = size;
  }

  public String getRoomId() {
    return this.roomId;
  }

  public String getRoomName() {
    return this.roomName;
  }

  public String getLandscapeToken() {
    return landscapeToken;
  }

  public int getSize() {
    return size;
  }
}
