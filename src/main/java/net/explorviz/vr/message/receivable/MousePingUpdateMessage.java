package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class MousePingUpdateMessage extends ReceivableMessage {

    public MousePingUpdateMessage() {
        super(EVENT);
    }

    public static final String EVENT = "mouse_ping_update";

    private String modelId;
    private boolean isApplication;
    private double[] position;

    public String getModelId() { return this.modelId; }

    public void setModelId(String modelId) { this.modelId = modelId; }

    public boolean getIsApplication() { return this.isApplication; }

    public void setIsApplication(boolean isApplication) { this.isApplication = isApplication; }

    public double[] getPosition() { return this.position; }

    public void setPosition() { this.position = position; }

    @Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleMousePingUpdateMessage(this, arg);
    }
}
