package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.VRMessage;

public class SendLandscapeMessage extends VRMessage {

    public static final String EVENT = "landscape";

    public static class LandscapeEntity {
        private String id;
        private boolean opened;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

    }

    public static class LandscapePosition {
        private double[] position;
        private double[] quaternion;

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

    public static class HighlightingObject {
        private long time;
        private String userID;
        private String appID;
        private String entityType;
        private String entityID;
        private boolean isHighlighted;

        public long getTime() {
            return time;
        }

        public void setTime(long l) {
            this.time = l;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getAppID() {
            return appID;
        }

        public void setAppID(String appID) {
            this.appID = appID;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        public String getEntityID() {
            return entityID;
        }

        public void setEntityID(String entityID) {
            this.entityID = entityID;
        }

        public boolean isHighlighted() {
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

        public String[] getOpenComponents() {
            return openComponents;
        }

        public void setOpenComponents(String[] openComponents) {
            this.openComponents = openComponents;
        }

    }

    private LandscapeEntity[] systems;
    private LandscapeEntity[] nodeGroups;
    private App[] openApps;
    private LandscapePosition landscape;

    public LandscapeEntity[] getSystems() {
        return systems;
    }

    public void setSystems(LandscapeEntity[] systems) {
        this.systems = systems;
    }

    public LandscapeEntity[] getNodeGroups() {
        return nodeGroups;
    }

    public void setNodeGroups(LandscapeEntity[] nodeGroups) {
        this.nodeGroups = nodeGroups;
    }

    public App[] getOpenApps() {
        return openApps;
    }

    public void setOpenApps(App[] openApps) {
        this.openApps = openApps;
    }

    public LandscapePosition getLandscape() {
        return landscape;
    }

    public void setLandscape(LandscapePosition landscape) {
        this.landscape = landscape;
    }

}
