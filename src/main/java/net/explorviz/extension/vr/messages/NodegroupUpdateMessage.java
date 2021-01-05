package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NodegroupUpdateMessage extends VRMessage {

    public static final String EVENT = "nodegroup_update";

    private String id;
    private boolean isOpen;

    @JsonCreator
    public NodegroupUpdateMessage(@JsonProperty("id") String id,  @JsonProperty("isOpen") boolean isOpen) {
        super();
        this.id = id;
        this.isOpen = isOpen;
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isOpen ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NodegroupUpdateMessage other = (NodegroupUpdateMessage) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (isOpen != other.isOpen) {
            return false;
        }
        return true;
    }
}
