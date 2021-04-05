package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class SpectatingUpdateMessage extends ReceivableMessage {

    public static final String EVENT = "spectating_update";

    private String userId;
    private boolean isSpectating;
    private String spectatedUser;

    public SpectatingUpdateMessage() {
        super(EVENT);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleSpectatingUpdateMessage(this, arg);
    }
}
