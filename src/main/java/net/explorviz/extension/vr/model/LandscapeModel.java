package net.explorviz.extension.vr.model;

/**
 * Model for the landscape entity.
 * 
 * The landscape only contains positional information.
 */
public class LandscapeModel extends BaseModel implements GrabbableObject {

    private double[] scale;

    public LandscapeModel(String id) {
        super(id);
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
