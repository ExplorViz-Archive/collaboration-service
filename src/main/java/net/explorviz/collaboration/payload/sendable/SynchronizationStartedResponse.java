package net.explorviz.collaboration.payload.sendable;

import net.explorviz.collaboration.model.ProjectorConfigurations;

public class SynchronizationStartedResponse {

  private RoomCreatedResponse roomResponse;
  private LobbyJoinedResponse joinResponse;
  private ProjectorConfigurations projectorConfigurations;

  public SynchronizationStartedResponse(final RoomCreatedResponse roomResponse, LobbyJoinedResponse joinResponse,
      ProjectorConfigurations projectorConfigurations) {
    this.roomResponse = roomResponse;
    this.joinResponse = joinResponse;
    this.projectorConfigurations = projectorConfigurations;
  }

  public RoomCreatedResponse getRoomResponse() {
    return this.roomResponse;
  }

  public LobbyJoinedResponse getJoinResponse() {
    return this.joinResponse;
  }

  public ProjectorConfigurations getProjectorConfigurations() {
    return this.projectorConfigurations;
  }

}
