package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HightlightingUpdateMessage extends VRMessage {

    public static final String EVENT = "hightlighting_update";

    private String appID;
    private String entityType;
    private String entityID;
    private boolean isHighlighted;

    @JsonCreator
    public HightlightingUpdateMessage(@JsonProperty("appID") String appID,
            @JsonProperty("entityType") String entityType, @JsonProperty("entityID") String entityID,
            @JsonProperty("isHighlighted") boolean isHighlighted) {
        super();
        this.appID = appID;
        this.entityType = entityType;
        this.entityID = entityID;
        this.isHighlighted = isHighlighted;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appID == null) ? 0 : appID.hashCode());
        result = prime * result + ((entityID == null) ? 0 : entityID.hashCode());
        result = prime * result + ((entityType == null) ? 0 : entityType.hashCode());
        result = prime * result + (isHighlighted ? 1231 : 1237);
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
        HightlightingUpdateMessage other = (HightlightingUpdateMessage) obj;
        if (appID == null) {
            if (other.appID != null) {
                return false;
            }
        } else if (!appID.equals(other.appID)) {
            return false;
        }
        if (entityID == null) {
            if (other.entityID != null) {
                return false;
            }
        } else if (!entityID.equals(other.entityID)) {
            return false;
        }
        if (entityType == null) {
            if (other.entityType != null) {
                return false;
            }
        } else if (!entityType.equals(other.entityType)) {
            return false;
        }
        if (isHighlighted != other.isHighlighted) {
            return false;
        }
        return true;
    }
}
