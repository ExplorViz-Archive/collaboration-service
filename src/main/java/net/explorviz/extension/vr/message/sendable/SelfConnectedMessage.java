package net.explorviz.extension.vr.message.sendable;

import java.awt.Color;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.explorviz.extension.vr.message.SendableMessage;
import net.explorviz.extension.vr.message.encoder.ColorSerializer;

public class SelfConnectedMessage extends SendableMessage {
    public static final String EVENT = "self_connected";

    public static class Controllers {
        private String controller1;
        private String controller2;

        public String getController1() {
            return controller1;
        }

        public void setController1(String controller1) {
            this.controller1 = controller1;
        }

        public String getController2() {
            return controller2;
        }

        public void setController2(String controller2) {
            this.controller2 = controller2;
        }
    }

    public static class User {
        private String id;
        private String name;

        @JsonSerialize(using = ColorSerializer.class)
        private Color color;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
    
    public static class OtherUser extends User {
        private Controllers controllers;

        public Controllers getControllers() {
            return controllers;
        }

        public void setControllers(Controllers controllers) {
            this.controllers = controllers;
        }
    }

    private User self;
    private OtherUser[] users;

    public SelfConnectedMessage() {
        super(EVENT);
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public OtherUser[] getUsers() {
        return users;
    }

    public void setUsers(OtherUser[] users) {
        this.users = users;
    }
}
