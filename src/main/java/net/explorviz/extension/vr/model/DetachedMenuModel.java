package net.explorviz.extension.vr.model;

public class DetachedMenuModel extends BaseModel implements GrabbableObject {
    
    private String entityType;
    private String detachId;
    private boolean isGrabbed;
    private String grabbedByUser;
    
    public DetachedMenuModel(String detachId, String entityType, String id) {
        super(id);
        this.detachId = detachId;
        this.entityType = entityType;
        this.isGrabbed = false;
        this.grabbedByUser = null;
    }
    
    public String getDetachId() {
        return this.detachId;
    }
    
    public void setDetachId(String detachId) {
        this.detachId = detachId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    
    public void setGrabbed(final boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
    }

    public boolean isGrabbed() {
        return isGrabbed;
    }

    public void setGrabbedByUser(final String userID) {
        this.grabbedByUser = userID;
    }

    @Override
    public String isGrabbedByUser() {
        return grabbedByUser;
    }
}
