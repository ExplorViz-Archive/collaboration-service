package net.explorviz.vr.payload.sendable;

public class RoomListRecord {
	private final String roomId;
	private final String roomName;

	public RoomListRecord(String roomId, String roomName) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}
}
