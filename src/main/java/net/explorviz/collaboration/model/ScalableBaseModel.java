package net.explorviz.collaboration.model;

public class ScalableBaseModel extends BaseModel {
  private double[] scale;

  public ScalableBaseModel(final String id) {
    super(id);
  }

  public double[] getScale() {
    return this.scale.clone();
  }

  public void setScale(final double[] scale) {
    this.scale = scale.clone();
  }
}
