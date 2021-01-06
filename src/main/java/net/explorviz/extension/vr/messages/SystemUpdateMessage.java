package net.explorviz.extension.vr.messages;

public class SystemUpdateMessage extends VRMessage {

    public static final String EVENT = "system_update";

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
    public <T> T handleWith(VRMessageHandler<T> handler) {
        return handler.handleSystemUpdateMessage(this);
    }
}
