package net.explorviz.collaboration.service.room.factory;

import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.HeatmapService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeatmapServiceFactory {

  public HeatmapService makeHeatmapService(final Room room) {
    return new HeatmapService();
  }

}
