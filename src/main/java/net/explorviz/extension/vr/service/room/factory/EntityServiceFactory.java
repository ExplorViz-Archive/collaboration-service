package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.room.EntityService;
import net.explorviz.extension.vr.service.room.GrabService;

@ApplicationScoped
public class EntityServiceFactory {

    @Inject
    IdGenerationService idGenerationService;

    public EntityService makeEntityService(GrabService grabService) {
        return new EntityService(idGenerationService, grabService);
    }
}
