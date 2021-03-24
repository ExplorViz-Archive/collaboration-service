package net.explorviz.extension.vr.model;

public class ScalableBaseModel extends BaseModel {
    private double[] scale;

    public ScalableBaseModel(String id) {
        super(id);
    }

    public double[] getScale() {
        return scale;
    }

    public void setScale(double[] scale) {
        this.scale = scale;
    }
}
