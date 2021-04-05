package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.BroadcastService;

@ApplicationScoped
public class BroadcastServiceFactory {

    public BroadcastService makeBroadcastService(Room room) {
    	final var sessionRegistry= room.getSessionRegistry();
        return new BroadcastService(sessionRegistry);
    }
}
