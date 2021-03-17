package net.explorviz.extension.vr.model;

public class DetachedMenuModel extends BaseModel implements GrabbableObject {

    private String entityType;
    private String detachId;
    private double[] scale;

    public DetachedMenuModel(String detachId, String entityType, String id) {
        super(id);
        this.detachId = detachId;
        this.entityType = entityType;
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

    @Override
    public double[] getScale() {
        return scale;
    }

    @Override
    public void setScale(double[] scale) {
        this.scale = scale;
    }
}
