package net.explorviz.vr.model;

public class ControllerModel extends BaseModel {

	private final int controllerId;
	private final String assetUrl;

	private double[] intersection;

	public ControllerModel(String id, int controllerId, String assetUrl) {
		super(id);
		this.controllerId = controllerId;
		this.assetUrl = assetUrl;
	}

	public int getControllerId() {
		return controllerId;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	public boolean hasIntersection() {
		return this.intersection != null;
	}

	public double[] getIntersection() {
		if (!this.hasIntersection())
			return null;
		return new double[] { this.intersection[0], this.intersection[1], this.intersection[2] };
	}

	public void setIntersection(double[] intersection) {
		if (intersection == null) {
			this.intersection = null;
			return;
		}
		if (!this.hasIntersection()) {
			this.intersection = new double[3];
		}
		this.intersection[0] = intersection[0];
		this.intersection[1] = intersection[1];
		this.intersection[2] = intersection[2];
	}
}
