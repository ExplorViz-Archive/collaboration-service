package net.explorviz.extension.vr.messages;

public class HightlightingUpdateMessage extends VRMessage {

    public static final String EVENT = "hightlighting_update";

    private String appID;
    private String entityType;
    private String entityID;
    private boolean isHighlighted;

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

    public boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    @Override
    public <T> T handleWith(VRMessageHandler<T> handler) {
        return handler.handleHightlightingUpdateMessage(this);
    }
}
