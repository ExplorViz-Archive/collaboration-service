package net.explorviz.collaboration.message.sendable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.awt.Color;
import net.explorviz.collaboration.message.SendableMessage;
import net.explorviz.collaboration.message.encoder.ColorSerializer;

public class SelfConnectedMessage extends SendableMessage {
  public static final String EVENT = "self_connected";

  private User self;
  private OtherUser[] users;


  public static class Controller {
    private int controllerId;
    private String assetUrl;
    private double[] position;
    private double[] quaternion;
    private double[] intersection;

    public int getControllerId() {
      return this.controllerId;
    }

    public void setControllerId(final int controllerId) {
      this.controllerId = controllerId;
    }

    public String getAssetUrl() {
      return this.assetUrl;
    }

    public void setAssetUrl(final String assetUrl) {
      this.assetUrl = assetUrl;
    }

    public double[] getPosition() {
      return this.position.clone();
    }

    public void setPosition(final double[] position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion.clone();
    }

    public double[] getIntersection() {
      return this.intersection.clone();
    }

    public void setIntersection(final double[] intersection) {
      this.intersection = intersection.clone();
    }
  }


  public static class User {
    private String id;
    private String name;

    @JsonSerialize(using = ColorSerializer.class)
    private Color color;

    public String getId() {
      return this.id;
    }

    public void setId(final String id) {
      this.id = id;
    }

    public String getName() {
      return this.name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public Color getColor() {
      return this.color;
    }

    public void setColor(final Color color) {
      this.color = color;
    }
  }


  public static class OtherUser extends User {
    private Controller[] controllers;
    private double[] position;
    private double[] quaternion;

    public Controller[] getControllers() {
      return this.controllers.clone();
    }

    public void setControllers(final Controller... controllers) {
      this.controllers = controllers.clone();
    }

    public double[] getPosition() {
      return this.position.clone();
    }

    public void setPosition(final double... position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double... quaternion) {
      this.quaternion = quaternion.clone();
    }
  }

  public SelfConnectedMessage() {
    super(EVENT);
  }

  public User getSelf() {
    return this.self;
  }

  public void setSelf(final User self) {
    this.self = self;
  }

  public OtherUser[] getUsers() {
    return this.users.clone();
  }

  public void setUsers(final OtherUser[] users) {
    this.users = users.clone();
  }
}
