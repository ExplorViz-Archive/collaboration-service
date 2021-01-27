package net.explorviz.extension.vr.model;

/**
 * Model for the landscape entity.
 * 
 * The landscape only contains positional information.
 */
public class LandscapeModel extends BaseModel implements GrabbableObject {

    private boolean isGrabbed = false;
    private String grabbedByUser;
    private double[] scale;

    public LandscapeModel(String id) {
        super(id);
    }

    @Override
    public boolean isGrabbed() {
        return isGrabbed;
    }

    @Override
    public void setGrabbed(boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
    }

    @Override
    public void setGrabbedByUser(String userId) {
        this.grabbedByUser = userId;
    }

    @Override
    public String isGrabbedByUser() {
        return grabbedByUser;
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
