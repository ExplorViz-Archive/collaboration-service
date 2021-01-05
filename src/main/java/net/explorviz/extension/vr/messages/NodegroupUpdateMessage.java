package net.explorviz.extension.vr.messages;

public class NodegroupUpdateMessage extends VRMessage {

    public static final String EVENT = "nodegroup_update";

    private String id;
    private Boolean isOpen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
