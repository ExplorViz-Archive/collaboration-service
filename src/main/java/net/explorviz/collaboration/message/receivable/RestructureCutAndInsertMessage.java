package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureCutAndInsertMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_cut_insert";

    private String destinationEntity;
    private String destinationId;
    private String clippedEntity;
    private String clippedEntityId;

    public RestructureCutAndInsertMessage(){
        super(EVENT);
    }

    public String getDestinationEntity() {
        return this.destinationEntity;
    }

    public void setDestinationEntity(final String destinationEntity) {
        this.destinationEntity = destinationEntity;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(final String destinationId) {
        this.destinationId = destinationId;
    }

    public String getClippedEntity() {
        return clippedEntity;
    }

    public void setClippedEntity(final String clippedEntity) {
        this.clippedEntity = clippedEntity;
    }

    public String getClippedEntityId() {
        return clippedEntityId;
    }

    public void setClippedEntityId(final String clippedEntityId) {
        this.clippedEntityId = clippedEntityId;
    }

    @Override
    public String toString() {
        return "destinationEntity: " + this.destinationEntity + ", destinationId: "
            + this.destinationId + ", clippedEntity: " + this.clippedEntity + ", clippedEntityId: " + this.clippedEntityId;
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureCutAndInsertMessage(this, arg);
    }
}