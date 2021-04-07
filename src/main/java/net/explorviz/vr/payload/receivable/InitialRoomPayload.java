package net.explorviz.vr.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a new
 * room is created. Contains all information to initialize a room.
 */
public class InitialRoomPayload {
    public static class Landscape {
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
    
    public static class App {
        private String id;
        private double[] position;
        private double[] quaternion;
        private String[] openComponents;
        private double[] scale;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String[] getOpenComponents() {
            return openComponents;
        }

        public void setOpenComponents(String[] openComponents) {
            this.openComponents = openComponents;
        }

        public double[] getScale() {
            return scale;
        }

        public void setScale(double[] scale) {
            this.scale = scale;
        }
    }
    
    
    public static class DetachedMenu {
        private String entityId;
        private String entityType;
        private double[] position;
        private double[] quaternion;
        private double[] scale;

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
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
    private App[] openApps;
    private DetachedMenu[] detachedMenus;

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }
    
    public DetachedMenu[] getDetachedMenus() {
        return detachedMenus;
    }

    public void setDetachedMenus(DetachedMenu[] detachedMenus) {
        this.detachedMenus = detachedMenus;
    }

    public App[] getOpenApps() {
        return openApps;
    }

    public void setOpenApps(App[] openApps) {
        this.openApps = openApps;
    }
}
