package net.explorviz.collaboration.message;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encoder for the JSON serialization of messages that can be sent from the backend to the
 * frontend.
 */
public class SendableMessageEncoder implements Encoder.TextStream<SendableMessage> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReceivableMessageDecoder.class);

  private ObjectMapper objectMapper;

  @Override
  public void init(final EndpointConfig config) {
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public void destroy() {
    // nothing to do
  }

  @Override
  public void encode(final SendableMessage message, final Writer writer)
      throws EncodeException, IOException {
    try {
      this.objectMapper.writeValue(writer, message);
    } catch (JsonGenerationException | JsonMappingException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Failed to encode message: {}", e.getMessage());
      }
      throw e;
    }
  }

  /**
   * Encodes the given single {@link VrMessage}.
   *
   * This method is intended for testing purposes only.
   *
   * @param json A JSON object.
   * @return The parsed message.
   * @throws DecodeException This exception should never be thrown.
   * @throws IOException     When there is an error encoding the message. See also
   *                         {@link JsonGenerationException} and {@link JsonMappingException}.
   */
  public String encodeMessage(final SendableMessage message) throws EncodeException, IOException {
    final StringWriter writer = new StringWriter();
    this.encode(message, writer);
    return writer.toString();
  }
}
