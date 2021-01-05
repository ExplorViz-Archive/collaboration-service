package net.explorviz.extension.vr.messages;

public class ComponentUpdateMessage extends VRMessage {

    public static final String EVENT = "component_update";

    private String appID;
    private String componentID;
    private Boolean isOpened;
    private Boolean isFoundation;

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

    public Boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(Boolean isOpened) {
        this.isOpened = isOpened;
    }

    public Boolean getIsFoundation() {
        return isFoundation;
    }

    public void setIsFoundation(Boolean isFoundation) {
        this.isFoundation = isFoundation;
    }
}
