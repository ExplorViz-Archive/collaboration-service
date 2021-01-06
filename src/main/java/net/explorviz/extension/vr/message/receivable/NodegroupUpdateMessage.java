package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class NodegroupUpdateMessage extends ReceivedMessage {

    public static final String EVENT = "nodegroup_update";

    private String id;
    private boolean isOpen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public <T> T handleWith(ReceivedMessageHandler<T> handler) {
        return handler.handleNodegroupUpdateMessage(this);
    }
}
