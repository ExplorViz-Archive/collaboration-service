package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class AppTranslatedMessage extends ReceivedMessage {

    public static final String EVENT = "app_translated";

    private String appId;
    private double[] direction;
    private double length;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public double[] getDirection() {
        return direction;
    }

    public void setDirection(double[] direction) {
        this.direction = direction;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleAppTranslatedMessage(this, arg);
    }
}
