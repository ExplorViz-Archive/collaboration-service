package net.explorviz.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.vr.service.Room;
import net.explorviz.vr.service.SessionRegistry;
import net.explorviz.vr.service.room.BroadcastService;

@ApplicationScoped
public class BroadcastServiceFactory {

	@Inject
	SessionRegistry sessionRegistry;

	public BroadcastService makeBroadcastService(Room room) {
		return new BroadcastService(sessionRegistry, (session) -> session.getRoom() == room);
	}
}
