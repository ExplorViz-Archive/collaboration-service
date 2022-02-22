package net.explorviz.collaboration.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.explorviz.collaboration.model.DetachedMenuModel;
import net.explorviz.collaboration.service.IdGenerationService;

public class DetachedMenuService {
  private final Map<String, DetachedMenuModel> detachedMenus = new ConcurrentHashMap<>();

  private final IdGenerationService idGenerationService;

  private final GrabService grabService;

  public DetachedMenuService(final IdGenerationService idGenerationService,
      final GrabService grabService) {
    this.idGenerationService = idGenerationService;
    this.grabService = grabService;
  }

  public Collection<DetachedMenuModel> getDetachedMenus() {
    return this.detachedMenus.values();
  }

  public String detachMenu(final String detachId, final String entityType, final double[] position,
      final double[] quaternion, final double[] scale) {
    final var objectId = this.idGenerationService.nextId();
    final var menu = new DetachedMenuModel(detachId, entityType, objectId);
    menu.setPosition(position);
    menu.setQuaternion(quaternion);
    menu.setScale(scale);
    this.detachedMenus.put(objectId, menu);
    this.grabService.addGrabbableObject(menu);
    return objectId;
  }

  public boolean closeDetachedMenu(final String menuId) {
    if (!this.grabService.isGrabbed(menuId)) {
      final var menu = this.detachedMenus.get(menuId);
      if (menu != null) {
        this.detachedMenus.remove(menuId);
        this.grabService.removeGrabbableObject(menu);
        return true;
      }
    }
    return false;
  }

  public void closeAllDetachedMenus() {
    for (final var menu : this.detachedMenus.values()) {
      this.grabService.removeGrabbableObject(menu);
    }
    this.detachedMenus.clear();
  }
}
