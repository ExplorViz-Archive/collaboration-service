package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.SessionRegistry;

@ApplicationScoped
public class BroadcastServiceFactory {

    public BroadcastService makeBroadcastService(SessionRegistry sessionRegistry) {
        return new BroadcastService(sessionRegistry);
    }
}
