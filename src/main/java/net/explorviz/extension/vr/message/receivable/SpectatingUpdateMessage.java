package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class SpectatingUpdateMessage extends ReceivableMessage {

    public static final String EVENT = "spectating_update";

    private String userID;
    private boolean isSpectating;
    private String spectatedUser;

    public SpectatingUpdateMessage() {
        super(EVENT);
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
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleSpectatingUpdateMessage(this, arg);
    }
}
