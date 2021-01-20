package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.BroadcastableMessage;

public class MenuDetachedForwardMessage extends BroadcastableMessage {
    private static final String EVENT = "menu_detached";

    private final String objectId;
    private final String entityType;
    private final String detachId;
    private double[] position;
    private double[] quaternion;

    public MenuDetachedForwardMessage(String objectId, String entityType, String detachId, double[] position, double[] quaternion) {
        super(EVENT);
        this.objectId = objectId;
        this.entityType = entityType;
        this.detachId = detachId;
        this.position = position;
        this.quaternion = quaternion;
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

    public String getObjectId() {
        return objectId;
    }

    public String getDetachId() {
        return detachId;
    }

    public String getEntityType() {
        return entityType;
    }
    
    

}
