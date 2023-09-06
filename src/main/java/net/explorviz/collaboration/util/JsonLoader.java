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
public class JsonLoader {

    public static Optional<ProjectorConfigurations> loadFromJsonResourceById(String resourceName, String id)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Get the URL of the resource
        URL resourceUrl = JsonLoader.class.getResource(resourceName);
        if (resourceUrl == null) {
            throw new FileNotFoundException("Resource not found: " + resourceName);
        }

        // Read the JSON file into a list of ProjectorConfigurations objects
        List<ProjectorConfigurations> configurations = objectMapper.readValue(resourceUrl,
                new TypeReference<List<ProjectorConfigurations>>() {
                });

        // Filter the list based on the id attribute to get the desired object
        return configurations.stream()
                .filter(config -> id.equals(config.getId()))
                .findFirst();
    }
}
