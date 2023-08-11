package net.explorviz.collaboration.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ApplicationModel extends ScalableBaseModel implements GrabbableObjectModel {
  private final List<String> openComponents;
  private final List<String> transparentComponents;

  public ApplicationModel(final String id) {
    super(id);
    this.openComponents = new ArrayList<>();
    this.transparentComponents = new ArrayList<>();
  }

  public void openComponent(final String id) {
    this.openComponents.add(id);
  }

 
  public void turnComponentsTransparent(final String[] ids){
    this.transparentComponents.clear();
    Collections.addAll(this.transparentComponents, ids);
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

  public List<String> getTransparentComponents() {
    return this.transparentComponents;
  }

  @Override
  public String getGrabId() {
    return this.getId();
  }
}
