package net.explorviz.extension.vr;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import net.explorviz.extension.vr.payload.receivable.InitialRoomPayload;
import net.explorviz.extension.vr.payload.receivable.InitialRoomPayload.App;
import net.explorviz.extension.vr.payload.receivable.InitialRoomPayload.DetachedMenu;
import net.explorviz.extension.vr.payload.receivable.JoinLobbyPayload;
import net.explorviz.extension.vr.payload.sendable.LobbyJoinedResponse;
import net.explorviz.extension.vr.payload.sendable.RoomCreatedResponse;
import net.explorviz.extension.vr.payload.sendable.RoomListRecord;
import net.explorviz.extension.vr.service.RoomService;
import net.explorviz.extension.vr.service.TicketService;

@Path("/v2/vr")
public class RoomResource {

	@Inject
	RoomService roomService;

	@Inject
	TicketService ticketService;

	/**
	 * Gets the IDs of all rooms.
	 */
	@GET
	@Path("/rooms")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RoomListRecord> listRooms() {
		return roomService.getRooms().stream().map((room) -> new RoomListRecord(room.getRoomId(), room.getName()))
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
	public RoomCreatedResponse addRoom(InitialRoomPayload body) {
		var room = roomService.createRoom();

		// Initialize landscape.
		var landscape = body.getLandscape();
		room.getLandscapeService().initLandscape(landscape.getLandscapeToken(), landscape.getTimestamp(),
				landscape.getPosition(), landscape.getQuaternion(), landscape.getScale());

		// Initialize applications.
		for (App app : body.getOpenApps()) {
			room.getApplicationService().openApplication(app.getId(), app.getPosition(), app.getQuaternion(),
					app.getScale());
			for (String componentId : app.getOpenComponents()) {
				room.getApplicationService().updateComponent(componentId, app.getId(), false, true);
			}
		}

		// Initialize detached menus.
		for (DetachedMenu menu : body.getDetachedMenus()) {
			room.getDetachedMenuService().detachMenu(menu.getEntityId(), menu.getEntityType(), menu.getPosition(),
					menu.getQuaternion(), menu.getScale());
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
	public LobbyJoinedResponse joinLobby(@PathParam("room-id") String roomId, JoinLobbyPayload body) {
		var room = roomService.lookupRoom(roomId);
		var user = room.getUserService().makeUserModel(body.getUserName());
		var ticket = ticketService.drawTicket(room, user);
		return new LobbyJoinedResponse(ticket.getTicketId(), ticket.getValidUntil().toEpochMilli());
	}
}
