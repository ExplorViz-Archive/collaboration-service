package net.explorviz.vr.model;

import java.util.ArrayList;

public class ApplicationModel extends ScalableBaseModel implements GrabbableObjectModel {
    private final ArrayList<String> openComponents;

    public ApplicationModel(String id) {
        super(id);
        openComponents = new ArrayList<>();
    }

    public void openComponent(String id) {
        openComponents.add(id);
    }

    public void closeComponent(String id) {
        openComponents.remove(id);
    }

    public void closeAllComponents() {
        openComponents.clear();
    }

    public ArrayList<String> getOpenComponents() {
        return openComponents;
    }
    
    @Override
    public String getGrabId() {
        return getId();
    }
}
