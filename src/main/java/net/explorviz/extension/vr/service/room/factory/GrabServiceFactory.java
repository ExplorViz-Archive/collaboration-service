package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.room.GrabService;

@ApplicationScoped
public class GrabServiceFactory {

    public GrabService makeGrabService() {
        return new GrabService();
    }
}
