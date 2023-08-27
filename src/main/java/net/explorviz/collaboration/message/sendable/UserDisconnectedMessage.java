package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.BroadcastableMessage;
import net.explorviz.collaboration.model.HighlightingModel;

public class UserDisconnectedMessage extends BroadcastableMessage {
  public static final String EVENT = "user_disconnect";
  private HighlightingModel[] highlightedComponents;

  private String id;

  public UserDisconnectedMessage() {
    super(EVENT);
    this.highlightedComponents = new HighlightingModel[0];
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public void setHighlightedComponents(HighlightingModel[] highlightedComponents){
    this.highlightedComponents = highlightedComponents.clone(); 
  }

  public HighlightingModel[] getHighlightedComponents(){
    return this.highlightedComponents.clone();
  }
}
