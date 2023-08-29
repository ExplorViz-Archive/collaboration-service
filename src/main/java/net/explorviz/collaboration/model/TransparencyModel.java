package net.explorviz.collaboration.model;

public class TransparencyModel {
   
    private final String transparencyInApp;
    private final String transparentEntityId;
    private final String entityType;

    public TransparencyModel(final String appId, final String entityId, final String entityType) {
        this.transparencyInApp = appId;
        this.transparentEntityId = entityId;
        this.entityType = entityType;
    }

    public String getTransparentApp() {
        return this.transparencyInApp;
    }

    public String getTransparentEntityId() {
        return this.transparentEntityId;
    }

    public String getEntityType() {
        return this.entityType;
    }
      
      
}
