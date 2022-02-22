package net.explorviz.collaboration.message;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encoder for the JSON deserialization of messages that were received by the backend from the
 * frontend.
 */
public class ReceivableMessageDecoder implements Decoder.TextStream<ReceivableMessage> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReceivableMessageDecoder.class);

  private ObjectMapper objectMapper;

  @Override
  public void init(final EndpointConfig config) {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    this.objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
  }

  @Override
  public void destroy() {
    // nothing to do
  }

  @Override
  public ReceivableMessage decode(final Reader reader) throws DecodeException, IOException {
    try {
      return this.objectMapper.readValue(reader, ReceivableMessage.class);
    } catch (JsonParseException | JsonMappingException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Failed to decode message: {}", e.getMessage());
      }
      throw e;
    }

  }

  /**
   * Decodes the given JSON string as a single {@link VrMessage}.
   *
   * This method is intended for testing purposes only.
   *
   * @param json A JSON object.
   * @return The parsed message.
   * @throws DecodeException This exception should never be thrown.
   * @throws IOException When there is an error parsing the message. See also
   *         {@link JsonParseException} and {@link JsonMappingException}.
   */
  public VrMessage decodeMessage(final String json) throws DecodeException, IOException {
    final Reader reader = new StringReader(json);
    return this.decode(reader);
  }
}
