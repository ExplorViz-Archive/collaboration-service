package net.explorviz.extension.vr.messages;

public class AppTranslatedMessage extends VRMessage {

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
    public <T> T handleWith(VRMessageHandler<T> handler) {
        return handler.handleAppTranslatedMessage(this);
    }
}
