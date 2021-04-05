package net.explorviz.extension.vr.model;

public class ControllerModel extends BaseModel {

	private String assetUrl;

	public ControllerModel(String id) {
		super(id);
	}

	public String getName() {
		return assetUrl;
	}

	public void setName(final String assetUrl) {
		this.assetUrl = assetUrl;
	}
}
