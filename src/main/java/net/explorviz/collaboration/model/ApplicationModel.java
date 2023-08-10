package net.explorviz.collaboration.model;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

public class ApplicationModel extends ScalableBaseModel implements GrabbableObjectModel {
  private final List<String> openComponents;
  //private final  HashMap<String, String> highlightedComponents; // key is component id, value is user id who highlighted the component

  public ApplicationModel(final String id) {
    super(id);
    this.openComponents = new ArrayList<>();
  }

  public void openComponent(final String id) {
    this.openComponents.add(id);
  }

  public void closeComponent(final String id) {
    this.openComponents.remove(id);
  }

  public void closeAllComponents() {
    this.openComponents.clear();
  }

  public List<String> getOpenComponents() {
    return this.openComponents;
  }

  @Override
  public String getGrabId() {
    return this.getId();
  }
}
