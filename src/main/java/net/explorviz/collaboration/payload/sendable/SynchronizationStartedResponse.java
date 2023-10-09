package net.explorviz.collaboration.payload.sendable;

import net.explorviz.collaboration.model.ProjectorConfigurations;

/**
 * Class provides the scaffold for the respone to the initiation of the synchronization feature.
 */
public class SynchronizationStartedResponse {

  final private RoomCreatedResponse roomResponse;
  final private LobbyJoinedResponse joinResponse;
  final private ProjectorConfigurations projectorConfigurations;

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
