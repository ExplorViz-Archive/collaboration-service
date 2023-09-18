package net.explorviz.collaboration.service;

import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides all informations about the synchronization feature of ExplorViz in
 * the ARENA2 of GEOMAR.
 */
@ApplicationScoped
public class SynchronizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizationService.class);

    // control barrier
    private boolean mainConnected;

    public boolean isMainConnected() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Main is connected!");
        }
        return this.mainConnected;
    }

    public void setMainConnected(final boolean mainIsConnected) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Main is connecting!");
        }
        this.mainConnected = mainIsConnected;
    }

}
