package net.explorviz.extension.vr;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.explorviz.extension.vr.payload.InitialRoomPayload;
import net.explorviz.extension.vr.payload.InitialRoomPayload.App;
import net.explorviz.extension.vr.payload.InitialRoomPayload.DetachedMenu;
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

        // Initialize landscape
        var landscape = body.getLandscape();
        room.getLandscapeService().initLandscape(landscape.getLandscapeToken(), landscape.getTimestamp(),
                landscape.getPosition(), landscape.getQuaternion(), landscape.getScale());
        
        // Initialize apps
        for(App app : body.getOpenApps()) {
            room.getApplicationService().openApplication(app.getId(), app.getPosition(), app.getQuaternion(), app.getScale());
            for(String componentId : app.getOpenComponents()) {
                room.getApplicationService().updateComponent(componentId, app.getId(), false, true);
            }
        }
        
        // Initialize detached menus
        for(DetachedMenu menu : body.getDetachedMenus()) {
            room.getDetachedMenuService().detachMenu(menu.getEntityId(), menu.getEntityType(), menu.getPosition(), 
                    menu.getQuaternion(), menu.getScale());
        }

        return room.getRoomId();
    }
}
