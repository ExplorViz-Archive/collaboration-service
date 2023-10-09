package net.explorviz.collaboration.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.explorviz.collaboration.model.ProjectorConfigurations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Util class to load json file.
 */
final public class JsonLoader {
  // private constructor to prevent instantiation
  private JsonLoader() {
  }

  ;

  // loads projectorconfigurations using deviceId
  public static Optional<ProjectorConfigurations> loadFromJsonResourceById(
      final String resourceName, final String id) throws IOException {
    // Get the URL of the resource
    final URL resourceUrl = JsonLoader.class.getResource(resourceName);
    if (resourceUrl == null) {
      throw new FileNotFoundException("Resource not found: " + resourceName);
    }

    // Read the JSON file into a list of ProjectorConfigurations objects
    final List<ProjectorConfigurations> configurations = new ObjectMapper().readValue(resourceUrl,
        new TypeReference<List<ProjectorConfigurations>>() {
        });

    // Filter the list based on the id attribute to get the desired object
    return configurations.stream().filter(config -> id.equals(config.getId())).findFirst();
  }
}
