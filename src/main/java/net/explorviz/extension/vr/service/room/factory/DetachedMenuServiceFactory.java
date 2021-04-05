package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.DetachedMenuService;

@ApplicationScoped
public class DetachedMenuServiceFactory {

    @Inject
    IdGenerationService idGenerationService;

    public DetachedMenuService makeDetachedMenuService(Room room) {
		final var grabService = room.getGrabService();
        return new DetachedMenuService(idGenerationService, grabService);
    }
}
