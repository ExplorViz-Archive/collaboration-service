package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.SendableMessage;

public class InitialLandscapeMessage extends SendableMessage {

    public static final String EVENT = "landscape";

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

    public static class DetachedMenu {
        private String objectId;
        private String entityType;
        private String entityId;
        private double[] position;
        private double[] quaternion;
        private double[] scale;

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

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

    public static class HighlightingObject {
        private String userId;
        private String appId;
        private String entityType;
        private String entityId;
        private boolean isHighlighted;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

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

        public boolean getIsHighlighted() {
            return isHighlighted;
        }

        public void setHighlighted(boolean isHighlighted) {
            this.isHighlighted = isHighlighted;
        }

    }

    public static class App {
        private String id;
        private double[] position;
        private double[] quaternion;
        private double[] scale;
        private String[] openComponents;
        private HighlightingObject[] highlightedComponents;

        public HighlightingObject[] getHighlightedComponents() {
            return highlightedComponents;
        }

        public void setHighlightedComponents(HighlightingObject[] highlightedComponents) {
            this.highlightedComponents = highlightedComponents;
        }

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

        public double[] getScale() {
            return scale;
        }

        public void setScale(double[] scale) {
            this.scale = scale;
        }

        public String[] getOpenComponents() {
            return openComponents;
        }

        public void setOpenComponents(String[] openComponents) {
            this.openComponents = openComponents;
        }
    }

    private App[] openApps;
    private Landscape landscape;
    private DetachedMenu[] detachedMenus;

    public InitialLandscapeMessage() {
        super(EVENT);
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

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }

}
