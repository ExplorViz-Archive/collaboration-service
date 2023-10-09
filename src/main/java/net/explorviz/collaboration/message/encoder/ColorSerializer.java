package net.explorviz.collaboration.message.encoder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.awt.Color;
import java.io.IOException;

/**
 * Custom JSON serializer for {@link Color}s that creates an array of RGB values.
 *
 * The RGB components are normalized to a range from {@code 0.0} to {@code 1.0}.
 */
public class ColorSerializer extends JsonSerializer<Color> {

  @Override
  public void serialize(final Color color, final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider) throws IOException {
    final var r = color.getRed() / 255.0;
    final var g = color.getGreen() / 255.0;
    final var b = color.getBlue() / 255.0;
    jsonGenerator.writeObject(new double[] {r, g, b});
  }
}
