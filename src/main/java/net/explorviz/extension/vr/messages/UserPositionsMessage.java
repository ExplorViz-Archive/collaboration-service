package net.explorviz.extension.vr.messages;

import java.util.Date;

public class UserPositionsMessage extends VRMessage {

    public static final String EVENT = "user-positions";

    public static class Controller {
        private double[] position;
        private double[] quaternion;
    }

    public static class Camera {
        private double[] positon;
        private double[] quaternion;
    }

    private Controller controller1;
    private Controller controller2;
    private Camera camera;
    private Date time;

    public Controller getController1() {
        return controller1;
    }

    public void setController1(Controller controller1) {
        this.controller1 = controller1;
    }

    public Controller getController2() {
        return controller2;
    }

    public void setController2(Controller controller2) {
        this.controller2 = controller2;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
