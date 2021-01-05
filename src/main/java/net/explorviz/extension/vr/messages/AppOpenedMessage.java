package net.explorviz.extension.vr.messages;

public class AppOpenedMessage extends VRMessage {
    
    public static final String EVENT = "app_opened";
    
    private String id;
    private double[] position;
    private double[] quaternion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(double[] quaternion) {
        this.quaternion = quaternion;
    }

}
