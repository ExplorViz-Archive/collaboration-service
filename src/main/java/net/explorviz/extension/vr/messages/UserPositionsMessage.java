package net.explorviz.extension.vr.messages;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPositionsMessage extends VRMessage {

    public static final String EVENT = "user-positions";

    public static class Pose {
        private double[] position;
        private double[] quaternion;

        @JsonCreator
        public Pose(@JsonProperty("position") double[] position, @JsonProperty("quaternion") double[] quaternion) {
            super();
            this.position = position;
            this.quaternion = quaternion;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(position);
            result = prime * result + Arrays.hashCode(quaternion);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Pose other = (Pose) obj;
            if (!Arrays.equals(position, other.position)) {
                return false;
            }
            if (!Arrays.equals(quaternion, other.quaternion)) {
                return false;
            }
            return true;
        }

        public double[] getPosition() {
            return position;
        }

        public void setPosition(double[] position) {
            this.position = position;
        }

        public double[] getQuaternion() {
            return quaternion;
        }

        public void setQuaternion(double[] quaternion) {
            this.quaternion = quaternion;
        }
    }

    private Pose controller1;
    private Pose controller2;
    private Pose camera;
    private Date time;

    @JsonCreator
    public UserPositionsMessage(@JsonProperty("controller1") Pose controller1,
            @JsonProperty("controller2") Pose controller2, @JsonProperty("camera") Pose camera,
            @JsonProperty("time") Date time) {
        super();
        this.controller1 = controller1;
        this.controller2 = controller2;
        this.camera = camera;
        this.time = time;
    }

    public Pose getController1() {
        return controller1;
    }

    public void setController1(Pose controller1) {
        this.controller1 = controller1;
    }

    public Pose getController2() {
        return controller2;
    }

    public void setController2(Pose controller2) {
        this.controller2 = controller2;
    }

    public Pose getCamera() {
        return camera;
    }

    public void setCamera(Pose camera) {
        this.camera = camera;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((camera == null) ? 0 : camera.hashCode());
        result = prime * result + ((controller1 == null) ? 0 : controller1.hashCode());
        result = prime * result + ((controller2 == null) ? 0 : controller2.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserPositionsMessage other = (UserPositionsMessage) obj;
        if (camera == null) {
            if (other.camera != null) {
                return false;
            }
        } else if (!camera.equals(other.camera)) {
            return false;
        }
        if (controller1 == null) {
            if (other.controller1 != null) {
                return false;
            }
        } else if (!controller1.equals(other.controller1)) {
            return false;
        }
        if (controller2 == null) {
            if (other.controller2 != null) {
                return false;
            }
        } else if (!controller2.equals(other.controller2)) {
            return false;
        }
        if (time == null) {
            if (other.time != null) {
                return false;
            }
        } else if (!time.equals(other.time)) {
            return false;
        }
        return true;
    }
}
