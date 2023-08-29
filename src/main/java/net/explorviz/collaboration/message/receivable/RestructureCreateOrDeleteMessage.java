package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureCreateOrDeleteMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_create_delete";

    private String action;
    private String entityType;
    private String name;
    private String language;
    private String entityId;

    public RestructureCreateOrDeleteMessage() {
        super(EVENT);
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(final String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(final String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        if(this.name != null) {
            return "action: " + this.action + ", entityType: " + this.entityType + ", name: " + this.name + ", language: " + this.language;
        } else {
            return "action: " + this.action + ", entityType: " + this.entityType + ", entityId: " + this.entityId;
        }
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureCreateOrDeleteMessage(this, arg);
    }
}

/*
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

    public String getDestinationEntity(final String destinationEntity) {
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
*/
