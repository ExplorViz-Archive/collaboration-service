package net.explorviz.collaboration.payload.sendable;

public class SynchronizationStartedResponse{

  private RoomCreatedResponse roomResponse;
  private LobbyJoinedResponse joinResponse;

  public SynchronizationStartedResponse(final RoomCreatedResponse roomResponse, LobbyJoinedResponse joinResponse) {
    this.roomResponse = roomResponse;
    this.joinResponse = joinResponse;
  }

  public RoomCreatedResponse getRoomResponse() {
    return this.roomResponse;
  }

  public LobbyJoinedResponse getJoinResponse() {
    return this.joinResponse;
  }
  
}
