package net.explorviz.extension.vr.model;

import java.util.ArrayList;

public class ApplicationModel extends BaseModel implements GrabbableObject {

    boolean isOpen;
    boolean isGrabbed;
    String grabbedByUser;

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

    public void setGrabbed(final boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
    }

    public boolean isGrabbed() {
        return isGrabbed;
    }

    public void setGrabbedByUser(final String userID) {
        this.grabbedByUser = userID;
    }

    @Override
    public String isGrabbedByUser() {
        return grabbedByUser;
    }

}
