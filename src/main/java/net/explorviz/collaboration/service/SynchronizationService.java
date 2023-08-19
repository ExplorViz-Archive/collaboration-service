package net.explorviz.collaboration.service;
import java.awt.Color;

import javax.inject.Inject;

import net.explorviz.collaboration.model.UserModel;

/**
 * Provides all informations about the synchronization feature of ExplorViz in the ARENA2 of GEOMAR.
 */
public class SynchronizationService {
    @Inject
    RoomService roomService;
    
    // synchronization room
    private Room room;
    // control instance
    private UserModel main;
    // synchronized devices
    private UserModel[] projectors = new UserModel[0];

    public void setRoom(String roomId) {
        this.room = this.roomService.createRoom(roomId);
    }

    public Room getRoom() {
        return this.room;
    }

    public void setMain(final String id, final String userName, final Color color, final double[] position, final double[] quaternion) {
        this.main = new UserModel(id, userName, color);
        if(position.length == 3) this.main.setPosition(position);
        if(quaternion.length == 4) this.main.setQuaternion(quaternion);
    }

    public UserModel getMain() {
        return this.main;
    }

    public void setProjectors(final String id, final String userName, final Color color, final double[] position, final double[] quaternion) {
        UserModel projector = new UserModel(id, userName, color);
        if(position.length == 3) projector.setPosition(position);
        if(quaternion.length == 4) projector.setQuaternion(quaternion);

        this.projectors = new UserModel[this.projectors.length + 1];
        this.projectors[this.projectors.length - 1] = projector;
    }

    public UserModel[] getProjectors() {
        return this.projectors;
    }

    public static class ProjectorConfigurations {
        private double[] yawPitchRoll = new double[3];
        private double[] projectorAngles = new double[4];

        public void setYawPitchRoll(double[] yawPitchRoll) {
            if(yawPitchRoll.length == 3) this.yawPitchRoll = yawPitchRoll;
        }

        public double[] getYawPitchRoll() {
            return this.yawPitchRoll;
        }

        public void setProjectorAngles(double[] projectorAngles) {
            if(projectorAngles.length == 4) this.projectorAngles = projectorAngles;
        }

        public double[] getProjectorAngles() {
            return this.projectorAngles;
        }

    }
}
