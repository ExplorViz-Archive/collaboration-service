package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class AppGrabbedMessage extends ReceivedMessage {

    public static final String EVENT = "app_grabbed";

    private String appID;
    private double[] appPosition;
    private double[] appQuaternion;
    private boolean isGrabbedByController1;
    private double[] controllerPosition;
    private double[] controllerQuaternion;

    public AppGrabbedMessage() {
        super(EVENT);
    }

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

    public boolean getIsGrabbedByController1() {
        return isGrabbedByController1;
    }

    public void setIsGrabbedByController1(boolean isGrabbedByController1) {
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

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleAppGrabbedMessage(this, arg);
    }
}
