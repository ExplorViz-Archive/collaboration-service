package net.explorviz.extension.vr.messages;

public class AppGrabbedMessage extends VRMessage {

    public static final String EVENT = "app_grabbed";

    private String appID;
    private double[] appPosition;
    private double[] appQuaternion;
    private Boolean isGrabbedByController1;
    private double[] controllerPosition;
    private double[] controllerQuaternion;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public double[] getAppPosition() {
        return appPosition;
    }

    public void setAppPosition(double[] appPosition) {
        this.appPosition = appPosition;
    }

    public double[] getAppQuaternion() {
        return appQuaternion;
    }

    public void setAppQuaternion(double[] appQuaternion) {
        this.appQuaternion = appQuaternion;
    }

    public Boolean getIsGrabbedByController1() {
        return isGrabbedByController1;
    }

    public void setIsGrabbedByController1(Boolean isGrabbedByController1) {
        this.isGrabbedByController1 = isGrabbedByController1;
    }

    public double[] getControllerPosition() {
        return controllerPosition;
    }

    public void setControllerPosition(double[] controllerPosition) {
        this.controllerPosition = controllerPosition;
    }

    public double[] getControllerQuaternion() {
        return controllerQuaternion;
    }

    public void setControllerQuaternion(double[] controllerQuaternion) {
        this.controllerQuaternion = controllerQuaternion;
    }

}
