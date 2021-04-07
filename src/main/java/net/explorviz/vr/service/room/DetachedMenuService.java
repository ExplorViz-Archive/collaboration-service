package net.explorviz.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.explorviz.vr.model.DetachedMenuModel;
import net.explorviz.vr.service.IdGenerationService;

public class DetachedMenuService {
	private final Map<String, DetachedMenuModel> detachedMenus = new ConcurrentHashMap<>();

	private final IdGenerationService idGenerationService;

	private final GrabService grabService;

	public DetachedMenuService(IdGenerationService idGenerationService, GrabService grabService) {
		this.idGenerationService = idGenerationService;
		this.grabService = grabService;
	}

	public Collection<DetachedMenuModel> getDetachedMenus() {
		return detachedMenus.values();
	}

	public String detachMenu(String detachId, String entityType, double[] position, double[] quaternion,
			double[] scale) {
		var objectId = idGenerationService.nextId();
		var menu = new DetachedMenuModel(detachId, entityType, objectId);
		menu.setPosition(position);
		menu.setQuaternion(quaternion);
		menu.setScale(scale);
		detachedMenus.put(objectId, menu);
		grabService.addGrabbableObject(menu);
		return objectId;
	}

	public boolean closeDetachedMenu(String menuId) {
		if (!grabService.isGrabbed(menuId)) {
			var menu = detachedMenus.get(menuId);
			if (menu != null) {
				detachedMenus.remove(menuId);
				grabService.removeGrabbableObject(menu);
				return true;
			}
		}
		return false;
	}
	
	public void closeAllDetachedMenus()  {
	    for (var menu : detachedMenus.values()) {
	        grabService.removeGrabbableObject(menu);
	    }
	    detachedMenus.clear();
	}
}
