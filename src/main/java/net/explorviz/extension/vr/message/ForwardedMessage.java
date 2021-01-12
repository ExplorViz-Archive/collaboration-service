package net.explorviz.extension.vr.message;

/**
 * A message that was received from the frontend and is forwarded to all other
 * users.
 */
public class ForwardedMessage extends SendableMessage {
    public static final String EVENT = "forward";

    /**
     * Type for flags that are used to indicate whether a message should be
     * forwarded or not.
     */
    public static enum ShouldForward {
        FORWARD, NO_FORWARD
    }

    /**
     * The ID of the user that sent the original message.
     */
    private String userID;

    /**
     * A message that was received from the frontend and should be forwarded to all
     * other users.
     */
    private ReceivableMessage originalMessage;

    /**
     * The default constructor used for decoding messages of this type.
     */
    public ForwardedMessage() {
        super(EVENT);
    }

    /**
     * A regular constructor that should be used instead of the default constructor
     * to construct a message of this type manually.
     * 
     * @param userID         The id of the user that sent the original message.
     * @param originaMessage The original message that is forwarded by this message.
     */
    public ForwardedMessage(String userID, ReceivableMessage originalMessage) {
        this();
        this.userID = userID;
        this.originalMessage = originalMessage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ReceivableMessage getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(ReceivableMessage originalMessage) {
        this.originalMessage = originalMessage;
    }
}
