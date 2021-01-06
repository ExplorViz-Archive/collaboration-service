package net.explorviz.extension.vr.messages;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VRMessageEncoder implements Encoder.TextStream<List<VRMessage>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRMessageDecoder.class);

    private ObjectMapper objectMapper;

    @Override
    public void init(EndpointConfig config) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void encode(List<VRMessage> messages, Writer writer) throws EncodeException, IOException {
        encodeObject(messages, writer);
    }

    /**
     * Encodes the given single {@link VRMessage}.
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
    public String encodeMessage(VRMessage message) throws EncodeException, IOException {
        StringWriter writer = new StringWriter();
        encodeObject(message, writer);
        return writer.toString();
    }

    /**
     * Common implementation of {@link encode} and {@link encodeMessage}.
     * 
     * @param <T>    The type of the object to encode.
     * @param object The object to encode as a JSON object.
     * @param writer The writer to write the generated JSON to.
     * @throws EncodeException This exception should never be thrown.
     * @throws IOException     When there is an error encoding the message. See also
     *                         {@link JsonGenerationException} and
     *                         {@link JsonMappingException}.
     */
    private <T> void encodeObject(T object, Writer writer) throws EncodeException, IOException {
        try {
            objectMapper.writeValue(writer, object);
        } catch (JsonGenerationException | JsonMappingException e) {
            LOGGER.error("Failed to encode message: {}", e.getMessage());
            throw e;
        }
    }
}
