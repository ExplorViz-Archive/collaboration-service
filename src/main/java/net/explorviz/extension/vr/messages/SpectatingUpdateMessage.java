package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpectatingUpdateMessage extends VRMessage {

    public static final String EVENT = "spectating_update";

    private String userID;
    private boolean isSpectating;
    private String spectatedUser;

    @JsonCreator
    public SpectatingUpdateMessage(@JsonProperty("userID") String userID,
            @JsonProperty("isSpectating") boolean isSpectating, @JsonProperty("spectatedUser") String spectatedUser) {
        super();
        this.userID = userID;
        this.isSpectating = isSpectating;
        this.spectatedUser = spectatedUser;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean getIsSpectating() {
        return isSpectating;
    }

    public void setIsSpectating(boolean isSpectating) {
        this.isSpectating = isSpectating;
    }

    public String getSpectatedUser() {
        return spectatedUser;
    }

    public void setSpectatedUser(String spectatedUser) {
        this.spectatedUser = spectatedUser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (isSpectating ? 1231 : 1237);
        result = prime * result + ((spectatedUser == null) ? 0 : spectatedUser.hashCode());
        result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
        SpectatingUpdateMessage other = (SpectatingUpdateMessage) obj;
        if (isSpectating != other.isSpectating) {
            return false;
        }
        if (spectatedUser == null) {
            if (other.spectatedUser != null) {
                return false;
            }
        } else if (!spectatedUser.equals(other.spectatedUser)) {
            return false;
        }
        if (userID == null) {
            if (other.userID != null) {
                return false;
            }
        } else if (!userID.equals(other.userID)) {
            return false;
        }
        return true;
    }
}
