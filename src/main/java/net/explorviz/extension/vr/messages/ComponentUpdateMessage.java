package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentUpdateMessage extends VRMessage {

    public static final String EVENT = "component_update";

    private String appID;
    private String componentID;
    private boolean isOpened;
    private boolean isFoundation;

    @JsonCreator
    public ComponentUpdateMessage(@JsonProperty("appID") String appID, @JsonProperty("componentID") String componentID,
            @JsonProperty("isOpened") boolean isOpened, @JsonProperty("isFoundation") boolean isFoundation) {
        super();
        this.appID = appID;
        this.componentID = componentID;
        this.isOpened = isOpened;
        this.isFoundation = isFoundation;
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

    public Boolean getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public Boolean getIsFoundation() {
        return isFoundation;
    }

    public void setIsFoundation(boolean isFoundation) {
        this.isFoundation = isFoundation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appID == null) ? 0 : appID.hashCode());
        result = prime * result + ((componentID == null) ? 0 : componentID.hashCode());
        result = prime * result + (isFoundation ? 1231 : 1237);
        result = prime * result + (isOpened ? 1231 : 1237);
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
        ComponentUpdateMessage other = (ComponentUpdateMessage) obj;
        if (appID == null) {
            if (other.appID != null) {
                return false;
            }
        } else if (!appID.equals(other.appID)) {
            return false;
        }
        if (componentID == null) {
            if (other.componentID != null) {
                return false;
            }
        } else if (!componentID.equals(other.componentID)) {
            return false;
        }
        if (isFoundation != other.isFoundation) {
            return false;
        }
        if (isOpened != other.isOpened) {
            return false;
        }
        return true;
    }
}
