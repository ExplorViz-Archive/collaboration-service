package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.SessionRegistry;
import net.explorviz.extension.vr.service.room.BroadcastService;

@ApplicationScoped
public class BroadcastServiceFactory {

    @Inject
    SessionRegistry sessionRegistry;
    
    public BroadcastService makeBroadcastService() {
        return new BroadcastService(sessionRegistry);
    }
}
