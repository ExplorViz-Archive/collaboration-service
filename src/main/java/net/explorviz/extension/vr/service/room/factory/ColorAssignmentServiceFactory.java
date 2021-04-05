package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;

@ApplicationScoped
public class ColorAssignmentServiceFactory {

    public ColorAssignmentService makeColorAssignmentService(Room room) {
        return new ColorAssignmentService();
    }

}
