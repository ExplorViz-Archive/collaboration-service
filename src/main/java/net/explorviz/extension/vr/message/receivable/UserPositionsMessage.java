package net.explorviz.extension.vr.message.receivable;

import java.util.Date;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class UserPositionsMessage extends ReceivedMessage {

    public static final String EVENT = "user_positions";

    public static class Pose {
        private double[] position;
        private double[] quaternion;

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

    private Pose controller1;
    private Pose controller2;
    private Pose camera;
    private Date time;

    public Pose getController1() {
        return controller1;
    }

    public void setController1(Pose controller1) {
        this.controller1 = controller1;
    }

    public Pose getController2() {
        return controller2;
    }

    public void setController2(Pose controller2) {
        this.controller2 = controller2;
    }

    public Pose getCamera() {
        return camera;
    }

    public void setCamera(Pose camera) {
        this.camera = camera;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleUserPositionsMessage(this, arg);
    }
}
