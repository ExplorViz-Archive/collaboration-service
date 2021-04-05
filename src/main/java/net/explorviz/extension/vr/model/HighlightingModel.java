package net.explorviz.extension.vr.model;

public class HighlightingModel {

	private final String highlightedApp;
	private final String highlightedEntity;
	private final String entityType;

	public HighlightingModel(boolean isHighlighted, String appId, String entityId, String entityType) {
		this.highlightedApp = appId;
		this.highlightedEntity = entityId;
		this.entityType = entityType;
	}

	public String getHighlightedApp() {
		return this.highlightedApp;
	}

	public String getHighlightedEntity() {
		return this.highlightedEntity;
	}

	public String getEntityType() {
		return this.entityType;
	}

}
