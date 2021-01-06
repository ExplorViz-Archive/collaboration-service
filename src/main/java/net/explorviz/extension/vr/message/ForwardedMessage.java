package net.explorviz.extension.vr.message;

/**
 * A message that was received from the frontend and is forwared to all other users.
 */
public class ForwardedMessage extends VRMessage {
    public static final String EVENT = "forward";

    /**
     * The ID of the user that sent the original message.
     */
    private String userID;
    
    /**
     * A message that was received from the frontend and should be forwarded to all other users.
     */
    private ReceivedMessage originalMessage;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ReceivedMessage getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(ReceivedMessage originalMessage) {
        this.originalMessage = originalMessage;
    }
}
