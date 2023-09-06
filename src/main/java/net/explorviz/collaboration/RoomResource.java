package net.explorviz.collaboration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.explorviz.collaboration.model.ProjectorConfigurations;
import net.explorviz.collaboration.payload.receivable.InitialRoomPayload;
import net.explorviz.collaboration.payload.receivable.InitialSynchronizationPayload;
import net.explorviz.collaboration.payload.receivable.InitialRoomPayload.App;
import net.explorviz.collaboration.payload.receivable.InitialRoomPayload.DetachedMenu;
import net.explorviz.collaboration.payload.receivable.JoinLobbyPayload;
import net.explorviz.collaboration.payload.sendable.LobbyJoinedResponse;
import net.explorviz.collaboration.payload.sendable.RoomCreatedResponse;
import net.explorviz.collaboration.payload.sendable.RoomListRecord;
import net.explorviz.collaboration.payload.sendable.SynchronizationStartedResponse;
import net.explorviz.collaboration.service.RoomService;
import net.explorviz.collaboration.service.SynchronizationService;
import net.explorviz.collaboration.util.JsonLoader;
import net.explorviz.collaboration.service.TicketService;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v2/vr")
public class RoomResource {

  @Inject
  /* default */RoomService roomService; // NOCS

  @Inject
  /* default */ TicketService ticketService; // NOCS

  @Inject
  SynchronizationService synchronizationService;

  /**
   * Gets the IDs of all rooms.
   */
  @GET
  @Path("/rooms")
  @Produces(MediaType.APPLICATION_JSON)
  public List<RoomListRecord> listRooms() {
    return this.roomService.getRooms().stream()
        .map((room) -> new RoomListRecord(room.getRoomId(), room.getName(),
            room.getLandscapeService().getLandscape().getLandscapeToken(),
            room.getUserService().getUsers().size()))
        .collect(Collectors.toList());
  }

  /**
   * Creates a new room with the given initial landscape, applications and
   * detached menus.
   *
   * @param body The initial room layout.
   * @return The ID of the newly created room.
   */
  @POST
  @Path("/room")
  @Produces(MediaType.APPLICATION_JSON)
  public RoomCreatedResponse addRoom(final InitialRoomPayload body) {
    // Check for wanted room id
    final var room = body.getRoomId() != null
        ? this.roomService.createRoom(body.getRoomId())
        : this.roomService.createRoom();

    // Initialize landscape.
    final var landscape = body.getLandscape();
    room.getLandscapeService().initLandscape(landscape.getLandscapeToken(),
        landscape.getTimestamp());

    // Initialize applications.
    for (final App app : body.getOpenApps()) {
      room.getApplicationService().openApplication(app.getId(), app.getPosition(),
          app.getQuaternion(), app.getScale());
      for (final String componentId : app.getOpenComponents()) {
        room.getApplicationService().updateComponent(componentId, app.getId(), false, true);
      }
    }

    // Initialize detached menus.
    for (final DetachedMenu menu : body.getDetachedMenus()) {
      room.getDetachedMenuService().detachMenu(menu.getEntityId(), menu.getEntityType(),
          menu.getPosition(), menu.getQuaternion(), menu.getScale());
    }

    return new RoomCreatedResponse(room.getRoomId());
  }

  /**
   * Adds a user to the lobby of the room with the given ID.
   *
   * @param roomId The ID of the room whose lobby to add the new user to.
   * @return A ticket ID that can be used to establish a websocket connection.
   */
  @POST
  @Path("/room/{room-id}/lobby")
  @Produces(MediaType.APPLICATION_JSON)
  public LobbyJoinedResponse joinLobby(@PathParam("room-id") final String roomId,
      final JoinLobbyPayload body) {
    final var room = this.roomService.lookupRoom(roomId);

    // Initialize user model.
    final var userModel = room.getUserService().makeUserModel(body.getUserName());
    userModel.setPosition(body.getPosition());
    userModel.setQuaternion(body.getQuaternion());

    final var ticket = this.ticketService.drawTicket(room, userModel);
    return new LobbyJoinedResponse(ticket.getTicketId(), ticket.getValidUntil().toEpochMilli());
  }

  /**
   * 
   * @param body The initial synchronization layout containing room.
   * @return the id of the synchronization room.
   */
  @POST
  @Path("/synchronization")
  @Produces(MediaType.APPLICATION_JSON)
  public SynchronizationStartedResponse startSynchronization(final InitialSynchronizationPayload body)
      throws IOException {
    InitialRoomPayload roomPayload = body.getRoomPayload();
    String roomId = roomPayload.getRoomId();
    // Check if room already exists
    Boolean needRoom = !this.roomService.roomExists(roomId);

    JoinLobbyPayload joinPayload = body.getJoinPayload();
    String deviceId = joinPayload.getUserName();

    // Handels
    // Notify service, main is connected
    if (deviceId.equals("Main")) {
      this.synchronizationService.setMainIsConnected(true);
    } else {
      // Let projector wait for control instance
      long startTime = System.currentTimeMillis();
      long timeout = 2000;
      while (System.currentTimeMillis() - startTime < timeout) {
        if (this.synchronizationService.getMainIsConnected()) {
          break;
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    RoomCreatedResponse roomResponse = new RoomCreatedResponse(roomId);
    // Same outcome, but acutally adds the room if room is needed.
    if (needRoom) {
      roomResponse = this.addRoom(roomPayload);
    }

    LobbyJoinedResponse joinResponse = this.joinLobby(roomResponse.getRoomId(), joinPayload);

    // Get the specific projectorconfigurations of json file
    Optional<ProjectorConfigurations> projectorConfigurations = JsonLoader
        .loadFromJsonResourceById("/projectorConfigurations.json", deviceId);

    // Not working due async
    // this.synchronizationService.setService(roomId, deviceId);

    SynchronizationStartedResponse synchronizationStartedResponse = new SynchronizationStartedResponse(roomResponse,
        joinResponse, projectorConfigurations.get());

    return synchronizationStartedResponse;
  }
}
