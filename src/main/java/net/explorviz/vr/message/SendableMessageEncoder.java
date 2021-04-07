package net.explorviz.vr.message;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encoder for the JSON serialization of messages that can be sent from the
 * backend to the frontend.
 */
public class SendableMessageEncoder implements Encoder.TextStream<SendableMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceivableMessageDecoder.class);

    private ObjectMapper objectMapper;

    @Override
    public void init(EndpointConfig config) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void encode(SendableMessage message, Writer writer) throws EncodeException, IOException {
        try {
            objectMapper.writeValue(writer, message);
        } catch (JsonGenerationException | JsonMappingException e) {
            LOGGER.error("Failed to encode message: {}", e.getMessage());
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
     *                         {@link JsonGenerationException} and
     *                         {@link JsonMappingException}.
     */
    public String encodeMessage(SendableMessage message) throws EncodeException, IOException {
        StringWriter writer = new StringWriter();
        encode(message, writer);
        return writer.toString();
    }
}
