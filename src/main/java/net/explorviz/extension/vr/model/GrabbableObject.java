package net.explorviz.extension.vr.model;

public interface GrabbableObject {

    public void setPosition(double[] position);

    public void setQuaternion(double[] quaternion);

    public double[] getScale();

    public void setScale(double[] scale);

}
