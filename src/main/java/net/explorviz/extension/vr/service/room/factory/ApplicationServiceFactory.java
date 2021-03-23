package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.room.ApplicationService;
import net.explorviz.extension.vr.service.room.GrabService;

@ApplicationScoped
public class ApplicationServiceFactory {
	public ApplicationService makeApplicationService(GrabService grabService) {
		return new ApplicationService(grabService);
	}
}
