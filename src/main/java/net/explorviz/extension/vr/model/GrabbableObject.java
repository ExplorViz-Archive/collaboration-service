package net.explorviz.extension.vr.model;

public interface GrabbableObject {

    public String getId();
    
    public double[] getPosition();
    
    public void setPosition(double[] position);

    public double[] getQuaternion();

    public void setQuaternion(double[] quaternion);

    public double[] getScale();

    public void setScale(double[] scale);

}
