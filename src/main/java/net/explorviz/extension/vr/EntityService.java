package net.explorviz.extension.vr;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.BaseModel;

@ApplicationScoped
public class EntityService {

	private final BaseModel landscape = new BaseModel(); // only containing positional information about landscape
	private boolean landscapePosChanged = false; // tells whether a user already manipulated
														// landscape
	private final HashMap<String, Boolean> systemState = new HashMap<>(); // tells if a system (systemID) is
														// opened/closed
	private final HashMap<String, Boolean> nodeGroupState = new HashMap<>(); // tells if a nodegroup (nodegroupID) is
															// opened/closed
	private final HashMap<String, ApplicationModel> apps = new HashMap<>(); // maps applicationID to the application
																			// model

	public void openApp(String appId, double[] position, double[] quaternion) {
		ApplicationModel appModel;
		;

		// add app to hash map or get app from hash map
		if (this.apps.containsKey(appId)) {
			appModel = this.apps.get(appId);
		} else {
			appModel = new ApplicationModel();
			appModel.setId(appId);
			this.apps.put(appId, appModel);
		}

		appModel.setOpen(true);
		appModel.setPosition(position);
		appModel.setQuaternion(quaternion);

	}

	public void closeApp(String appId) {
		this.apps.remove(appId);
	}

	public boolean grabbApp(String appId, String userId) {
		ApplicationModel appModel = this.apps.get(appId);
		if (appModel == null || appModel.isGrabbed())
			return false;
		appModel.setGrabbed(true);
		appModel.setGrabbedByUser(userId);
		return true;
	}

	public void releaseApp(String appId, double[] position, double[] quaternion) {
		ApplicationModel appModel;

		// add app to hash map or get app from hash map
		if (this.apps.containsKey(appId)) {
			appModel = this.apps.get(appId);
		} else {
			appModel = new ApplicationModel();
			appModel.setId(appId);
			this.apps.put(appId, appModel);
		}

		appModel.setOpen(true);
		appModel.setPosition(position);
		appModel.setQuaternion(quaternion);
		appModel.setGrabbed(false);
	}

	public void translateApp() {

	}

	public void updateComponent(String componentId, String appId, boolean isFoundation, boolean isOpened) {
		ApplicationModel appModel = this.apps.get(appId);

		if (appModel != null) {
			if (isFoundation) {
				appModel.closeAllComponents();
			} else if (isOpened) {
				appModel.openComponent(componentId);
			} else {
				appModel.closeComponent(componentId);
			}
		}

	}
	
	public void updateLandscapePosition(double[] offset, double[] quaternion) {
		this.landscapePosChanged = true;
	    landscape.setPosition(offset);
	    landscape.setQuaternion(quaternion);
	}
	
	public void updateSystem(String systemId, boolean systemOpened) {
        systemState.put(systemId, systemOpened);
	}
	
	public void updateNodegroup(String nodeGroupId, boolean nodeGroupOpened) {
        nodeGroupState.put(nodeGroupId, nodeGroupOpened);
	}

}
