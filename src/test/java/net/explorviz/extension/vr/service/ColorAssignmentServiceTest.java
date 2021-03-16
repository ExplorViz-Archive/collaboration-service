package net.explorviz.extension.vr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ColorAssignmentServiceTest {

    @Inject
    ColorAssignmentService service;
    
    @Test
    void testAssignColor() {
        var color1 = service.assignColor();
        var color2 = service.assignColor();
        assertNotEquals(color1, color2);
    }
    
    @Test
    void testUnassignColor() {
        var color1 = service.assignColor();
        service.unassignColor(color1);
        var color2 = service.assignColor();
        assertEquals(color1, color2);
    }

}
