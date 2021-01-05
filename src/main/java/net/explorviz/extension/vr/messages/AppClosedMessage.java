package net.explorviz.extension.vr.messages;

public class AppClosedMessage extends VRMessage {

    public static final String EVENT = "app_closed";

    private String appID;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
