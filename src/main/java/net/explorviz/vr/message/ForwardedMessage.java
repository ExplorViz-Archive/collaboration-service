package net.explorviz.vr.message;

/**
 * A message that was received from the frontend and is forwarded to all other
 * users.
 */
public class ForwardedMessage extends BroadcastableMessage {
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
    private final String userId;

    /**
     * A message that was received from the frontend and should be forwarded to all
     * other users.
     */
    private final ReceivableMessage originalMessage;

    /**
     * A regular constructor that should be used instead of the default constructor
     * to construct a message of this type manually.
     * 
     * @param userId         The id of the user that sent the original message.
     * @param originaMessage The original message that is forwarded by this message.
     */
    public ForwardedMessage(String userId, ReceivableMessage originalMessage) {
        super(EVENT);
        this.userId = userId;
        this.originalMessage = originalMessage;
    }

    public String getUserId() {
        return userId;
    }

    public ReceivableMessage getOriginalMessage() {
        return originalMessage;
    }
}
