package net.explorviz.collaboration.model;

public class DetachedMenuModel extends ScalableBaseModel implements GrabbableObjectModel {

  private String entityType;
  private String detachId;

  public DetachedMenuModel(final String detachId, final String entityType, final String id) {
    super(id);
    this.detachId = detachId;
    this.entityType = entityType;
  }

  public String getDetachId() {
    return this.detachId;
  }

  public void setDetachId(final String detachId) {
    this.detachId = detachId;
  }

  public String getEntityType() {
    return this.entityType;
  }

  public void setEntityType(final String entityType) {
    this.entityType = entityType;
  }

  @Override
  public String getGrabId() {
    return this.getId();
  }
}
