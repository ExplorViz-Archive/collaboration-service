package net.explorviz.extension.vr.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class VRMessageDecoderTest {
    VRMessageDecoder decoder;

    @BeforeEach
    void initDecoder() {
        decoder = new VRMessageDecoder();
        decoder.init(new EndpointConfig() {

            @Override
            public Map<String, Object> getUserProperties() {
                return new HashMap<>();
            }

            @Override
            public List<Class<? extends Encoder>> getEncoders() {
                return new ArrayList<>();
            }

            @Override
            public List<Class<? extends Decoder>> getDecoders() {
                return Arrays.asList(VRMessageDecoder.class);
            }
        });
    }

    @AfterEach
    void destroyDecoder() {
        decoder.destroy();
        decoder = null;
    }

    @Test
    public void testAppClosesdMessage() throws DecodeException, IOException {
        String json = "{\"event\": \"app_closed\", \"appId\": \"test\"}";
        VRMessage message = decoder.decodeMessage(json);
        assertEquals(message, new AppClosedMessage("test"));
    }
}
