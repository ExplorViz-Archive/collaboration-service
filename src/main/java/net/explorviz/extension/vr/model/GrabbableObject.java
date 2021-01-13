package net.explorviz.extension.vr.model;

public interface GrabbableObject {

    public boolean isGrabbed();
    
    public void setGrabbed(boolean isGrabbed);
    
    public void setGrabbedByUser(String userId);
    
    public String isGrabbedByUser();
    
    public void setPosition(double[] position);
    
    public void setQuaternion(double[] quaternion);
    
}
