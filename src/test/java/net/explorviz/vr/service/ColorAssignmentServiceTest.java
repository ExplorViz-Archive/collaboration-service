package net.explorviz.vr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import net.explorviz.collaboration.service.room.ColorAssignmentService;
import org.junit.jupiter.api.Test;

class ColorAssignmentServiceTest {

  @Test
  void testAssignColor() {
    final var service = new ColorAssignmentService();
    final var color1 = service.assignColor();
    final var color2 = service.assignColor();
    assertNotEquals(color1, color2);
  }

  @Test
  void testUnassignColor() {
    final var service = new ColorAssignmentService();
    final var color1 = service.assignColor();
    service.unassignColor(color1);
    final var color2 = service.assignColor();
    assertEquals(color1, color2);
  }

}
