package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class UserPositionsMessage extends ReceivableMessage {

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

    public static class ControllerPose extends Pose {
        private double[] intersection;

        public double[] getIntersection() {
            return intersection;
        }

        public void setIntersection(double[] intersection) {
            this.intersection = intersection;
        }
    }

    private ControllerPose controller1;
    private ControllerPose controller2;
    private Pose camera;

    public UserPositionsMessage() {
        super(EVENT);
    }

    public ControllerPose getController1() {
        return controller1;
    }

    public void setController1(ControllerPose controller1) {
        this.controller1 = controller1;
    }

    public ControllerPose getController2() {
        return controller2;
    }

    public void setController2(ControllerPose controller2) {
        this.controller2 = controller2;
    }

    public Pose getCamera() {
        return camera;
    }

    public void setCamera(Pose camera) {
        this.camera = camera;
    }
 
    @Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleUserPositionsMessage(this, arg);
    }
}
