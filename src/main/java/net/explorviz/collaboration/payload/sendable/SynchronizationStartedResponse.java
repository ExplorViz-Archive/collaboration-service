package net.explorviz.collaboration.payload.sendable;

import net.explorviz.collaboration.model.ProjectorConfigurations;

/**
 * Class provides the scaffold for the respone to the initiation of the synchronization feature.
 */
public class SynchronizationStartedResponse {

  private final RoomCreatedResponse roomResponse;
  private final LobbyJoinedResponse joinResponse;
  private final ProjectorConfigurations projectorConfigurations;

  public SynchronizationStartedResponse(final RoomCreatedResponse roomResponse,
      final LobbyJoinedResponse joinResponse,
      final ProjectorConfigurations projectorConfigurations) {
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
