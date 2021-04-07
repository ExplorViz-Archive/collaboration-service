package net.explorviz.vr.model;

public interface GrabbableObjectModel {

    public String getGrabId();
    
    public double[] getPosition();
    
    public void setPosition(double[] position);

    public double[] getQuaternion();

    public void setQuaternion(double[] quaternion);

    public double[] getScale();

    public void setScale(double[] scale);

}
