package net.explorviz.extension.vr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import net.explorviz.extension.vr.service.room.ColorAssignmentService;

class ColorAssignmentServiceTest {

    @Test
    void testAssignColor() {
        var service = new ColorAssignmentService();
        var color1 = service.assignColor();
        var color2 = service.assignColor();
        assertNotEquals(color1, color2);
    }
    
    @Test
    void testUnassignColor() {
        var service = new ColorAssignmentService();
        var color1 = service.assignColor();
        service.unassignColor(color1);
        var color2 = service.assignColor();
        assertEquals(color1, color2);
    }

}
