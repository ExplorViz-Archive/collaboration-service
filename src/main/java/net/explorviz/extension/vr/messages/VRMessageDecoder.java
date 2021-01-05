package net.explorviz.extension.vr.messages;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VRMessageDecoder implements Decoder.TextStream<List<VRMessage>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRMessageDecoder.class);

    private ObjectMapper objectMapper;

    @Override
    public void init(EndpointConfig config) {
        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }

    @Override
    public void destroy() {
    }

    @Override
    public List<VRMessage> decode(Reader reader) throws DecodeException, IOException {
        try {
            return objectMapper.readValue(reader, new TypeReference<List<VRMessage>>() {
            });
        } catch (JsonParseException | JsonMappingException e) {
            LOGGER.error("Failed to decode message: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Decodes the given JSON string as a single {@link VRMessage}.
     * 
     * This method is intended for testing purposes only.
     * 
     * @param json A JSON object.
     * @return The parsed message.
     * @throws IOException     When there is an error parsing the message. See also
     *                         {@link JsonParseException} and
     *                         {@link JsonMappingException}.
     * @throws DecodeException This exception should never be thrown.
     */
    public VRMessage decodeMessage(String json) throws DecodeException, IOException {
        return objectMapper.readValue(json, VRMessage.class);
    }
}
