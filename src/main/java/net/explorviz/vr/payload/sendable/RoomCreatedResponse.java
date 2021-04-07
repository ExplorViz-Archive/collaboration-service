package net.explorviz.vr.payload.sendable;

public class RoomCreatedResponse {
	private final String roomId;

	public RoomCreatedResponse(String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomId() {
		return roomId;
	}
}
