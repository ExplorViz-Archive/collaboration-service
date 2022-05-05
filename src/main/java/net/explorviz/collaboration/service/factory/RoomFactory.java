package net.explorviz.collaboration.service.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.*;
import net.explorviz.collaboration.service.room.factory.*;

@ApplicationScoped
public class RoomFactory {

  @Inject
  /* default */ UserServiceFactory userServiceFactory; // NOCS

  @Inject
  /* default */ HeatmapServiceFactory heatmapServiceFactory; // NOCS

  @Inject
  /* default */ LandscapeServiceFactory landscapeServiceFactory;// NOCS

  @Inject
  /* default */ ApplicationServiceFactory applicationServiceFactory;// NOCS

  @Inject
  /* default */ DetachedMenuServiceFactory detachedMenuServiceFactory;// NOCS

  @Inject
  /* default */ BroadcastServiceFactory broadcastServiceFactory;// NOCS

  @Inject
  /* default */ ColorAssignmentServiceFactory colorAssignmentServiceFactory;// NOCS

  @Inject
  /* default */ GrabServiceFactory grabServiceFactory;// NOCS

  public Room makeRoom(final String roomId, final String roomName) {
    return new Room(roomId, roomName) {
      private UserService userService;

      private HeatmapService heatmapService;
      private GrabService grabService;
      private LandscapeService landscapeService;
      private ApplicationService applicationService;
      private DetachedMenuService detachedMenuService;
      private BroadcastService broadcastService;
      private ColorAssignmentService colorAssignmentService;

      @Override
      public UserService getUserService() {
        if (this.userService == null) {
          this.userService = RoomFactory.this.userServiceFactory.makeUserService(this);
        }
        return this.userService;
      }

      @Override
      public HeatmapService getHeatmapService() {
        if (this.heatmapService == null) {
          this.heatmapService = RoomFactory.this.heatmapServiceFactory.makeHeatmapService(this);
        }
        return this.heatmapService;
      }

      @Override
      public GrabService getGrabService() {
        if (this.grabService == null) {
          this.grabService = RoomFactory.this.grabServiceFactory.makeGrabService(this);
        }
        return this.grabService;
      }

      @Override
      public LandscapeService getLandscapeService() {
        if (this.landscapeService == null) {
          this.landscapeService =
              RoomFactory.this.landscapeServiceFactory.makeLandscapeService(this);
        }
        return this.landscapeService;
      }

      @Override
      public ApplicationService getApplicationService() {
        if (this.applicationService == null) {
          this.applicationService =
              RoomFactory.this.applicationServiceFactory.makeApplicationService(this);
        }
        return this.applicationService;
      }

      @Override
      public DetachedMenuService getDetachedMenuService() {
        if (this.detachedMenuService == null) {
          this.detachedMenuService =
              RoomFactory.this.detachedMenuServiceFactory.makeDetachedMenuService(this);
        }
        return this.detachedMenuService;
      }

      @Override
      public BroadcastService getBroadcastService() {
        if (this.broadcastService == null) {
          this.broadcastService =
              RoomFactory.this.broadcastServiceFactory.makeBroadcastService(this);
        }
        return this.broadcastService;
      }

      @Override
      public ColorAssignmentService getColorAssignmentService() {
        if (this.colorAssignmentService == null) {
          this.colorAssignmentService =
              RoomFactory.this.colorAssignmentServiceFactory.makeColorAssignmentService(this);
        }
        return this.colorAssignmentService;
      }
    };
  }
}
