package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppClosedMessage extends VRMessage {

    public static final String EVENT = "app_closed";

    private String appID;

    @JsonCreator
    public AppClosedMessage(@JsonProperty("appID") String appID) {
        super(EVENT);
        this.appID = appID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appID == null) ? 0 : appID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppClosedMessage other = (AppClosedMessage) obj;
        if (appID == null) {
            if (other.appID != null)
                return false;
        } else if (!appID.equals(other.appID))
            return false;
        return true;
    }
}
