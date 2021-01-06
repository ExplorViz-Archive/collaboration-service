package net.explorviz.extension.vr;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.extension.vr.messages.VRMessage;
import net.explorviz.extension.vr.messages.VRMessageDecoder;
import net.explorviz.extension.vr.messages.VRMessageEncoder;

@ServerEndpoint(value = "/v2/vr", decoders = { VRMessageDecoder.class }, encoders = { VRMessageEncoder.class })
@ApplicationScoped
public class VRSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRSocket.class);

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.debug("opened websocket");
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("closed websocket");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("websocket error: {}", throwable.getMessage());
    }

    @OnMessage
    public void onMessageList(List<VRMessage> messages) {
        for (VRMessage message : messages) {
            onMessage(message);
        }
    }

    private void onMessage(VRMessage message) {
        LOGGER.debug("received message: {}", message);
    }
}
