package net.explorviz.extension.vr.service;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * A service that is used by the user service to assign different colors to each
 * user.
 */
@ApplicationScoped
public class ColorAssignmentService {

    /**
     * The colors that can be assigned by this service.
     */
    private static final Color[] colors = {
            new Color(0, 167, 250), // blue
            new Color(209, 0, 209), // pink
            new Color(219, 208, 0), // yellow
            new Color(0, 209, 188), // turquoise
            new Color(209, 0, 209), // pink
            new Color(189, 126, 217), // purple
            new Color(0, 175, 206), // ocean blue
    };

    /**
     * Counts how often each of the {@link colors} has been assigned already.
     */
    private Map<Color, Integer> counters = new LinkedHashMap<>();

    public ColorAssignmentService() {
        // Initialize all counters with zero.
        for (Color color : colors) {
            counters.put(color, 0);
        }
    }

    /**
     * Gets the next color that has been assigned the least.
     * 
     * @return The assigned color.
     */
    public Color assignColor() {
        int minCount = Integer.MAX_VALUE;
        Color minCountColor = null;
        for (Map.Entry<Color, Integer> entry : counters.entrySet()) {
            final var color = entry.getKey();
            final var count = entry.getValue();
            if (count < minCount) {
                minCountColor = color;
                minCount = count;
            }
        }
        counters.put(minCountColor, minCount + 1);
        return minCountColor;
    }

    /**
     * Makes the given color assignable to another user.
     * 
     * @param color The color to unassign.
     */
    public void unassignColor(Color color) {
        if (counters.containsKey(color)) {
            int count = counters.get(color);
            counters.put(color, count - 1);
        }
    }
}
