package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.room.SessionRegistry;

@ApplicationScoped
public class SessionRegistryFactory {
    
    public SessionRegistry makeSessionRegistry() {
        return new SessionRegistry();
    }

}
