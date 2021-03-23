package net.explorviz.extension.vr.model;

/**
 * Model for the landscape entity.
 */
public class LandscapeModel extends BaseModel implements GrabbableObject {
    private String landscapeToken;
    private int timestamp;
    private double[] scale;

    public LandscapeModel(String id) {
        super(id);
    }

    public String getLandscapeToken() {
        return landscapeToken;
    }

    public void setLandscapeToken(String landscapeToken) {
        this.landscapeToken = landscapeToken;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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
