package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessageHandler;
import net.explorviz.extension.vr.message.RequestMessage;

public class MenuDetachedMessage extends RequestMessage {

    public static final String EVENT = "menu_detached";

    private String detachId;
    private String entityType;
    private double[] position;
    private double[] quaternion;
    private double[] scale;

    public MenuDetachedMessage() {
        super(EVENT);
    }

    public String getDetachId() {
        return detachId;
    }

    public String getEntityType() {
        return entityType;
    }

    public double[] getPosition() {
        return position;
    }

    public double[] getQuaternion() {
        return quaternion;
    }

    public void setDetachId(String detachId) {
        this.detachId = detachId;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setPosition(double[] position) {
        this.position = position;
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

    @Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleMenuDetachedMessage(this, arg);
    }

}
