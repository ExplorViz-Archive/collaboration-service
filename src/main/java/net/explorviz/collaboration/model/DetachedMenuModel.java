package net.explorviz.collaboration.model;

public class DetachedMenuModel extends ScalableBaseModel implements GrabbableObjectModel {

  private String entityType;
  private String detachId;
  private String userId;

  public DetachedMenuModel(final String detachId, final String userId, final String entityType,
      final String id) {
    super(id);
    this.detachId = detachId;
    this.userId = userId;
    this.entityType = entityType;
  }

  public String getDetachId() {
    return this.detachId;
  }

  public void setDetachId(final String detachId) {
    this.detachId = detachId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
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
