package net.explorviz.collaboration.model;

public class ProjectorConfigurations {
    private String id;
    private YawPitchRoll yawPitchRoll;
    private ProjectorAngles projectorAngles;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setYawPitchRoll(YawPitchRoll yawPitchRoll) {
        this.yawPitchRoll = yawPitchRoll;
    }

    public YawPitchRoll getYawPitchRoll() {
        return this.yawPitchRoll;
    }

    public void setProjectorAngles(ProjectorAngles projectorAngles) {
        this.projectorAngles = projectorAngles;
    }

    public ProjectorAngles getProjectorAngles() {
        return this.projectorAngles;
    }

    public static class YawPitchRoll {
        private double yaw;
        private double pitch;
        private double roll;

        public YawPitchRoll() {
        }

        public YawPitchRoll(double yaw, double pitch, double roll) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.roll = roll;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

    }

    public static class ProjectorAngles {
        private double up;
        private double down;
        private double left;
        private double right;

        public ProjectorAngles() {
        }

        public ProjectorAngles(double up, double down, double left, double right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        public double getUp() {
            return up;
        }

        public void setUp(double up) {
            this.up = up;
        }

        public double getDown() {
            return down;
        }

        public void setDown(double down) {
            this.down = down;
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getRight() {
            return right;
        }

        public void setRight(double right) {
            this.right = right;
        }

    }
}