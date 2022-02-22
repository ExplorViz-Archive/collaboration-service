package net.explorviz.collaboration.service;

import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;

/**
 * A service that provides a generator for IDs.
 *
 * The generates IDs are decimal numbers in ascending order. This service is used to create all
 * kinds of model identifiers. The same instance of this service is used by all rooms.
 */
@ApplicationScoped
public class IdGenerationService {
  private final AtomicLong idGenerator = new AtomicLong();

  public String nextId() {
    return String.valueOf(this.idGenerator.incrementAndGet());
  }
}
