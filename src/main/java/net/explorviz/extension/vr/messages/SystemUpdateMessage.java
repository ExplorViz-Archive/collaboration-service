package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemUpdateMessage extends VRMessage {

    public static final String EVENT = "system_update";

    private String id;
    private Boolean isOpen;

    @JsonCreator
    public SystemUpdateMessage(@JsonProperty("id") String id, @JsonProperty("isOpen") Boolean isOpen) {
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

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isOpen == null) ? 0 : isOpen.hashCode());
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
        SystemUpdateMessage other = (SystemUpdateMessage) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (isOpen == null) {
            if (other.isOpen != null) {
                return false;
            }
        } else if (!isOpen.equals(other.isOpen)) {
            return false;
        }
        return true;
    }
}
