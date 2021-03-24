package net.explorviz.extension.vr.service.room;

import net.explorviz.extension.vr.model.LandscapeModel;
import net.explorviz.extension.vr.service.IdGenerationService;

public class LandscapeService {
    private final GrabService grabService;
    private final LandscapeModel landscape;

    public LandscapeService(IdGenerationService idGenerationService, GrabService grabService) {
        this.grabService = grabService;
        this.landscape = new LandscapeModel(idGenerationService.nextId());
    }

    public LandscapeModel getLandscape() {
        return landscape;
    }
    
    public void initLandscape(String landscapeToken, long timestamp, double[] position, double[] quaternion, double[] scale) {
        landscape.setLandscapeToken(landscapeToken);
        landscape.setTimestamp(timestamp);
        landscape.setPosition(position);
        landscape.setQuaternion(quaternion);
        landscape.setScale(scale);
        grabService.addGrabbableObject(landscape);
    }

    public void updateTimestamp(long timestamp) {
        landscape.setTimestamp(timestamp);
        centerLandscape();
    }
    
    private void centerLandscape() {
        landscape.setPosition(new double[] { 0.0, 0.0, 0.0 });
        landscape.setQuaternion(-Math.sqrt(0.5), 0, 0, Math.sqrt(0.5));
        landscape.setScale(new double[] { 0.1, 0.1, 0.1 });
    }
}
