package net.explorviz.vr.message.sendable;

import net.explorviz.vr.message.BroadcastableMessage;

public class MenuDetachedForwardMessage extends BroadcastableMessage {
    private static final String EVENT = "menu_detached";

    private final String objectId;
    private final String entityType;
    private final String detachId;
    private double[] position;
    private double[] quaternion;
    private double[] scale;

    public MenuDetachedForwardMessage(String objectId, String entityType, String detachId, double[] position,
            double[] quaternion, double[] scale) {
        super(EVENT);
        this.objectId = objectId;
        this.entityType = entityType;
        this.detachId = detachId;
        this.position = position;
        this.quaternion = quaternion;
        this.scale = scale;
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

    public double[] getScale() {
        return scale;
    }

    public void setScale(double[] scale) {
        this.scale = scale;
    }

}
