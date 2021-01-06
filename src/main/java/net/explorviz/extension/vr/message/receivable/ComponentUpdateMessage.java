package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class ComponentUpdateMessage extends ReceivedMessage {

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

    @Override
    public <T> T handleWith(ReceivedMessageHandler<T> handler) {
        return handler.handleComponentUpdateMessage(this);
    }
}
