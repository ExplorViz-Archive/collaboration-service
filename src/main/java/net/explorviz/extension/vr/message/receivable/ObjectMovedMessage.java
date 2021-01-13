package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class ObjectMovedMessage extends ReceivableMessage {

    public static final String EVENT = "object_translated";

    private String objectId;
    private double[] position;
    private double[] quaternion;

    public ObjectMovedMessage() {
        super(EVENT);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    @Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleObjectMovedMessage(this, arg);
    }
}
