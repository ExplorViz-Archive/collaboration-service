package net.explorviz.extension.vr.messages;

public class ComponentUpdateMessage extends VRMessage {

    public static final String EVENT = "component_update";

    private String appID;
    private String componentID;
    private boolean isOpened;
    private boolean isFoundation;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

    public boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public boolean getIsFoundation() {
        return isFoundation;
    }

    public void setIsFoundation(boolean isFoundation) {
        this.isFoundation = isFoundation;
    }
}
