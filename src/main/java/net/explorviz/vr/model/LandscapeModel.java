package net.explorviz.vr.model;

/**
 * Model for the landscape entity.
 */
public class LandscapeModel extends ScalableBaseModel implements GrabbableObjectModel {
	private String landscapeToken;
	private long timestamp;

	public LandscapeModel(String id) {
		super(id);
	}

	public String getLandscapeToken() {
		return landscapeToken;
	}

	public void setLandscapeToken(String landscapeToken) {
		this.landscapeToken = landscapeToken;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String getGrabId() {
		return landscapeToken;
	}
}
