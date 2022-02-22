package net.explorviz.collaboration.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.explorviz.collaboration.model.ApplicationModel;

public class ApplicationService {
  private final Map<String, ApplicationModel> openApplications = new ConcurrentHashMap<>();

  private final GrabService grabService;

  public ApplicationService(final GrabService grabService) {
    this.grabService = grabService;
  }

  public Collection<ApplicationModel> getApplications() {
    return this.openApplications.values();
  }

  public void openApplication(final String appId, final double[] position,
      final double[] quaternion, final double[] scale) {
    final ApplicationModel appModel = this.getOrCreateApplication(appId);
    appModel.setPosition(position);
    appModel.setQuaternion(quaternion);
    appModel.setScale(scale);
  }

  public boolean closeApplication(final String appId) {
    if (!this.grabService.isGrabbed(appId)) {
      final var app = this.openApplications.get(appId);
      if (app != null) {
        this.openApplications.remove(appId);
        this.grabService.removeGrabbableObject(app);
        return true;
      }
    }
    return false;
  }

  public void closeAllApplications() {
    for (final var app : this.openApplications.values()) {
      this.grabService.removeGrabbableObject(app);
    }
    this.openApplications.clear();
  }

  public void updateComponent(final String componentId, final String appId,
      final boolean isFoundation, final boolean isOpened) {
    final ApplicationModel appModel = this.getOrCreateApplication(appId);
    if (isFoundation) {
      appModel.closeAllComponents();
    } else if (isOpened) {
      appModel.openComponent(componentId);
    } else {
      appModel.closeComponent(componentId);
    }
  }

  /**
   * Gets the application model with the given ID or creates a model if it does not exist.
   *
   * @param appId The ID of the application to get or create.
   * @return The (created) application.
   */
  private ApplicationModel getOrCreateApplication(final String appId) {
    if (this.openApplications.containsKey(appId)) {
      return this.openApplications.get(appId);
    }

    final var appModel = new ApplicationModel(appId);
    this.openApplications.put(appId, appModel);
    this.grabService.addGrabbableObject(appModel);
    return appModel;
  }
}
