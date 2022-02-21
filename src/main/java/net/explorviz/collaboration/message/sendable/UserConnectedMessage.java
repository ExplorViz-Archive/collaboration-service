package net.explorviz.collaboration.message.sendable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.awt.Color;
import net.explorviz.collaboration.message.BroadcastableMessage;
import net.explorviz.collaboration.message.encoder.ColorSerializer;

public class UserConnectedMessage extends BroadcastableMessage {
  public static final String EVENT = "user_connected";

  private String id;
  private String name;

  @JsonSerialize(using = ColorSerializer.class)
  private Color color;

  private double[] position;
  private double[] quaternion;

  public UserConnectedMessage() {
    super(EVENT);
  }

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

  public double[] getPosition() {
    return this.position;
  }

  public void setPosition(final double[] position) {
    this.position = position;
  }

  public double[] getQuaternion() {
    return this.quaternion;
  }

  public void setQuaternion(final double[] quaternion) {
    this.quaternion = quaternion;
  }
}
