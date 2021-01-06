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

import net.explorviz.extension.vr.messages.AppClosedMessage;
import net.explorviz.extension.vr.messages.AppGrabbedMessage;
import net.explorviz.extension.vr.messages.AppOpenedMessage;
import net.explorviz.extension.vr.messages.AppReleasedMessage;
import net.explorviz.extension.vr.messages.AppTranslatedMessage;
import net.explorviz.extension.vr.messages.ComponentUpdateMessage;
import net.explorviz.extension.vr.messages.HightlightingUpdateMessage;
import net.explorviz.extension.vr.messages.LandscapePositionMessage;
import net.explorviz.extension.vr.messages.NodegroupUpdateMessage;
import net.explorviz.extension.vr.messages.SpectatingUpdateMessage;
import net.explorviz.extension.vr.messages.SystemUpdateMessage;
import net.explorviz.extension.vr.messages.UserControllersMessage;
import net.explorviz.extension.vr.messages.UserPositionsMessage;
import net.explorviz.extension.vr.messages.VRMessage;
import net.explorviz.extension.vr.messages.VRMessageDecoder;
import net.explorviz.extension.vr.messages.VRMessageEncoder;
import net.explorviz.extension.vr.messages.VRMessageHandler;

@ServerEndpoint(value = "/v2/vr", decoders = { VRMessageDecoder.class }, encoders = { VRMessageEncoder.class })
@ApplicationScoped
public class VRSocket implements VRMessageHandler<Void> {

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
        if (messages == null)
            return;

        for (VRMessage message : messages) {
            handleMessage(message);
        }
    }

    /**
     * Called for each message that is received.
     * 
     * The received message is logged and the corresponding method from
     * {@link VRMessageHandler} is invoked.
     * 
     * @param message The received message.
     */
    public void handleMessage(VRMessage message) {
        if (message == null)
            return;

        LOGGER.debug("received message: {}", message);
        message.handleWith(null);
    }

    @Override
    public Void handleAppClosedMessage(AppClosedMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleAppGrabbedMessage(AppGrabbedMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleAppOpenedMessage(AppOpenedMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleAppReleasedMessage(AppReleasedMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleAppTranslatedMessage(AppTranslatedMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleComponentUpdateMessage(ComponentUpdateMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleHightlightingUpdateMessage(HightlightingUpdateMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleLandscapePositionMessage(LandscapePositionMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleNodegroupUpdateMessage(NodegroupUpdateMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleSpectatingUpdateMessage(SpectatingUpdateMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleSystemUpdateMessage(SystemUpdateMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleUserControllersMessage(UserControllersMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void handleUserPositionsMessage(UserPositionsMessage message) {
        // TODO Auto-generated method stub
        return null;
    }
}
