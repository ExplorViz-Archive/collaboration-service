package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.SessionRegistry;
import net.explorviz.extension.vr.service.room.BroadcastService;

@ApplicationScoped
public class BroadcastServiceFactory {

	@Inject
	SessionRegistry sessionRegistry;
	
    public BroadcastService makeBroadcastService(Room room) {
        return new BroadcastService(sessionRegistry, (session) -> session.getRoom() == room);
    }
}
