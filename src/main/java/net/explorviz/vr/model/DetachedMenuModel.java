package net.explorviz.vr.model;

public class DetachedMenuModel extends ScalableBaseModel implements GrabbableObjectModel {

	private String entityType;
	private String detachId;

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
	public String getGrabId() {
		return getId();
	}
}
