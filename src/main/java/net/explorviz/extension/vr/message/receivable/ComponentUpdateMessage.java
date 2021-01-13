package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class ComponentUpdateMessage extends ReceivableMessage {

    public static final String EVENT = "component_update";

    private String appID;
    private String componentID;
    private boolean isOpened;
    private boolean isFoundation;

    public ComponentUpdateMessage() {
        super(EVENT);
    }

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
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleComponentUpdateMessage(this, arg);
    }
}
