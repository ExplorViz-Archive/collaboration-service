package net.explorviz.extension.vr.messages;

public class NodegroupUpdateMessage extends VRMessage {

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
}
