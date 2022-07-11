package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.HeatmapService;

@ApplicationScoped public class HeatmapServiceFactory {

  public HeatmapService makeHeatmapService(final Room room) {
    return new HeatmapService();
  }

}
