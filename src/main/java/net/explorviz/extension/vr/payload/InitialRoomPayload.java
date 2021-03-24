package net.explorviz.extension.vr.payload;

/**
 * A JSON object that is sent by the frontend to the room resource when a new
 * room is created. Contains all information to initialize a room.
 */
public class InitialRoomPayload {
    public class Landscape {
        private String landscapeToken;
        private long timestamp;
        private double[] position;
        private double[] quaternion;
        private double[] scale;

        public String getLandscapeToken() {
            return landscapeToken;
        }

        public void setLandscapeToken(String landscapeToken) {
            this.landscapeToken = landscapeToken;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
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

        public double[] getScale() {
            return scale;
        }

        public void setScale(double[] scale) {
            this.scale = scale;
        }
    }

    private Landscape landscape;

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }
}
