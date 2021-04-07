package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class ComponentUpdateMessage extends ReceivableMessage {

    public static final String EVENT = "component_update";

    private String appId;
    private String componentId;
    private boolean isOpened;
    private boolean isFoundation;

    public ComponentUpdateMessage() {
        super(EVENT);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
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
