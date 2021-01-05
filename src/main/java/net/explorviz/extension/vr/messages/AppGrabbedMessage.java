package net.explorviz.extension.vr.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppGrabbedMessage extends VRMessage {

    public static final String EVENT = "app_grabbed";

    private String appID;
    private double[] appPosition;
    private double[] appQuaternion;
    private boolean isGrabbedByController1;
    private double[] controllerPosition;
    private double[] controllerQuaternion;

    @JsonCreator
    public AppGrabbedMessage(@JsonProperty("appID") String appID, @JsonProperty("appPosition") double[] appPosition,
            @JsonProperty("appQuaternion") double[] appQuaternion,
            @JsonProperty("isGrabbedByController1") boolean isGrabbedByController1,
            @JsonProperty("controllerPosition") double[] controllerPosition,
            @JsonProperty("controllerQuaternion") double[] controllerQuaternion) {
        super(EVENT);
        this.appID = appID;
        this.appPosition = appPosition;
        this.appQuaternion = appQuaternion;
        this.isGrabbedByController1 = isGrabbedByController1;
        this.controllerPosition = controllerPosition;
        this.controllerQuaternion = controllerQuaternion;
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appID == null) ? 0 : appID.hashCode());
        result = prime * result + Arrays.hashCode(appPosition);
        result = prime * result + Arrays.hashCode(appQuaternion);
        result = prime * result + Arrays.hashCode(controllerPosition);
        result = prime * result + Arrays.hashCode(controllerQuaternion);
        result = prime * result + (isGrabbedByController1 ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AppGrabbedMessage other = (AppGrabbedMessage) obj;
        if (appID == null) {
            if (other.appID != null) {
                return false;
            }
        } else if (!appID.equals(other.appID)) {
            return false;
        }
        if (!Arrays.equals(appPosition, other.appPosition)) {
            return false;
        }
        if (!Arrays.equals(appQuaternion, other.appQuaternion)) {
            return false;
        }
        if (!Arrays.equals(controllerPosition, other.controllerPosition)) {
            return false;
        }
        if (!Arrays.equals(controllerQuaternion, other.controllerQuaternion)) {
            return false;
        }
        if (isGrabbedByController1 != other.isGrabbedByController1) {
            return false;
        }
        return true;
    }
}
