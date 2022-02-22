package net.explorviz.collaboration.service.room;

import net.explorviz.collaboration.model.LandscapeModel;
import net.explorviz.collaboration.service.IdGenerationService;

public class LandscapeService {

  private static final double[] LANDSCAPE_CENTER_POSITION = {0.0, 0.0, 0.0};
  private static final double[] LANDSCAPE_CENTER_SCALE = {0.1, 0.1, 0.1};

  private final GrabService grabService;
  private final LandscapeModel landscape;

  public LandscapeService(final IdGenerationService idGenerationService,
      final GrabService grabService) {
    this.grabService = grabService;
    this.landscape = new LandscapeModel(idGenerationService.nextId());
  }

  public LandscapeModel getLandscape() {
    return this.landscape;
  }

  public void initLandscape(final String landscapeToken, final long timestamp,
      final double[] position, final double[] quaternion, final double[] scale) {
    this.landscape.setLandscapeToken(landscapeToken);
    this.landscape.setTimestamp(timestamp);
    this.landscape.setPosition(position);
    this.landscape.setQuaternion(quaternion);
    this.landscape.setScale(scale);
    this.grabService.addGrabbableObject(this.landscape);
  }

  public void updateTimestamp(final long timestamp) {
    this.landscape.setTimestamp(timestamp);
    this.centerLandscape();
  }

  private void centerLandscape() {
    this.landscape.setPosition(LANDSCAPE_CENTER_POSITION);
    this.landscape.setQuaternion(-Math.sqrt(0.5), 0, 0, Math.sqrt(0.5)); // NOCS
    this.landscape.setScale(LANDSCAPE_CENTER_SCALE);
  }
}
