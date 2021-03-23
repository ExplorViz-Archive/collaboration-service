package net.explorviz.extension.vr.service.room;

import net.explorviz.extension.vr.model.LandscapeModel;
import net.explorviz.extension.vr.service.IdGenerationService;

public class LandscapeService {
    private final LandscapeModel landscape;

    public LandscapeService(IdGenerationService idGenerationService, GrabService grabService) {
        this.landscape = new LandscapeModel(idGenerationService.nextId());
        grabService.addGrabbableObject(landscape);
        centerLandscape();
    }

    public LandscapeModel getLandscape() {
        return landscape;
    }
    
    public void centerLandscape() {
        landscape.setPosition(new double[] { 0.0, 0.0, 0.0 });
        landscape.setQuaternion(-Math.sqrt(0.5), 0, 0, Math.sqrt(0.5));
        landscape.setScale(new double[] { 0.1, 0.1, 0.1 });
    }
}
