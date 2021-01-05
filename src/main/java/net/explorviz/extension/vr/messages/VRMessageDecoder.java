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

    private final Logger LOGGER = LoggerFactory.getLogger(VRMessageDecoder.class);

    ObjectMapper objectMapper;

    @Override
    public void init(EndpointConfig config) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
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
}
