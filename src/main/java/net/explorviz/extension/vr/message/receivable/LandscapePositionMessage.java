package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class LandscapePositionMessage extends ReceivableMessage {

    public static final String EVENT = "landscape_position";

    private double[] position;
    private double[] quaternion;

    public LandscapePositionMessage() {
        super(EVENT);
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
        return handler.handleLandscapePositionMessage(this, arg);
    }
}
