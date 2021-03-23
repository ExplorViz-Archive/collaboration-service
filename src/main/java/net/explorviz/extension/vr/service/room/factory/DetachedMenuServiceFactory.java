package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.room.DetachedMenuService;
import net.explorviz.extension.vr.service.room.GrabService;

@ApplicationScoped
public class DetachedMenuServiceFactory {

    @Inject
    IdGenerationService idGenerationService;

    public DetachedMenuService makeDetachedMenuService(GrabService grabService) {
        return new DetachedMenuService(idGenerationService, grabService);
    }
}
