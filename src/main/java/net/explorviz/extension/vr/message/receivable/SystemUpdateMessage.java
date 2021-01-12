package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class SystemUpdateMessage extends ReceivableMessage {

    public static final String EVENT = "system_update";

    private String id;
    private boolean isOpen;

    public SystemUpdateMessage() {
        super(EVENT);
    }

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
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleSystemUpdateMessage(this, arg);
    }
}
