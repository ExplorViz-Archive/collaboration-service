package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.ColorAssignmentService;

@ApplicationScoped
public class ColorAssignmentServiceFactory {

  public ColorAssignmentService makeColorAssignmentService(final Room room) {
    return new ColorAssignmentService();
  }

}
