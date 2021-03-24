package net.explorviz.extension.vr;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.explorviz.extension.vr.payload.InitialRoomPayload;
import net.explorviz.extension.vr.service.RoomService;

@Path("/v2/vr")
public class RoomResource {

    @Inject
    RoomService roomService;

    @GET
    @Path("/rooms")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> list() {
        return roomService.getRooms();
    }

    @POST
    @Path("/room")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(InitialRoomPayload body) {
        var room = roomService.createRoom();

        // Initialize room.
        var landscape = body.getLandscape();
        room.getLandscapeService().initLandscape(landscape.getLandscapeToken(), landscape.getTimestamp(),
                landscape.getPosition(), landscape.getQuaternion(), landscape.getScale());

        return room.getRoomId();
    }
}
