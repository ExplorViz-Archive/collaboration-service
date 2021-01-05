package net.explorviz.extension.vr.messages;

public class SpectatingUpdateMessage extends VRMessage {

    public static final String EVENT = "spectating_update";

    private String userID;
    private boolean isSpectating;
    private String spectatedUser;

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
}
