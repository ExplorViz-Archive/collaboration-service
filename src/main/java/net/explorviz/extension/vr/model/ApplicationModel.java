package net.explorviz.extension.vr.model;

import java.util.ArrayList;

public class ApplicationModel extends ScalableBaseModel implements GrabbableObject {

    private boolean isOpen;

    private final ArrayList<String> openComponents;

    public ApplicationModel(String id) {
        super(id);
        openComponents = new ArrayList<>();
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(final boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void openComponent(final String id) {
        openComponents.add(id);
    }

    public void closeComponent(final String id) {
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
