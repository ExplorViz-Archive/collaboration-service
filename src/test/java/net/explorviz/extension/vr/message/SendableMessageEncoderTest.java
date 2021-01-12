package net.explorviz.extension.vr.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.NodegroupUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SystemUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.UserDisconnectedMessage;

public class SendableMessageEncoderTest {
    /**
     * A custom comparator for strings that
     */
    private static Comparator<String> ignoreWhitespace = new Comparator<>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.replaceAll("\\s+", "").compareTo(s2.replaceAll("\\s+", ""));
        }
    };

    private SendableMessageEncoder encoder;

    @BeforeEach
    void initEncodwr() {
        encoder = new SendableMessageEncoder();
        encoder.init(new EndpointConfig() {
            @Override
            public Map<String, Object> getUserProperties() {
                return new HashMap<>();
            }

            @Override
            public List<Class<? extends Encoder>> getEncoders() {
                return Arrays.asList(SendableMessageEncoder.class);
            }

            @Override
            public List<Class<? extends Decoder>> getDecoders() {
                return new ArrayList<>();
            }
        });
    }

    @AfterEach
    void destroyDecoder() {
        encoder.destroy();
        encoder = null;
    }

    @Test
    public void testForwardedAppClosesdMessage() throws EncodeException, IOException {
        final var message = new AppClosedMessage();
        message.setAppID("foo");
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"app_closed\", \"appID\": \"foo\" }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedAppGrabbedMessage() throws EncodeException, IOException {
        final var message = new AppGrabbedMessage();
        message.setAppID("foo");
        message.setAppPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setAppQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.setIsGrabbedByController1(true);
        message.setControllerPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setControllerQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"app_grabbed\", \"appID\": \"foo\", \"appPosition\": [1.0, 2.0, 3.0],"
                + "  \"appQuaternion\": [1.0, 2.0, 3.0, 4.0],"
                + "  \"isGrabbedByController1\": true, \"controllerPosition\": [1.0, 2.0, 3.0],"
                + "  \"controllerQuaternion\": [1.0, 2.0, 3.0, 4.0] }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedAppOpenedMessage() throws EncodeException, IOException {
        final var message = new AppOpenedMessage();
        message.setId("foo");
        message.setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"app_opened\", \"id\": \"foo\", \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedAppReleasedMessage() throws EncodeException, IOException {
        final var message = new AppReleasedMessage();
        message.setId("foo");
        message.setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected ="{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                +  "{ \"event\": \"app_released\", \"id\": \"foo\",  \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedAppTranslatedMessage() throws EncodeException, IOException {
        final var message = new AppTranslatedMessage();
        message.setAppId("foo");
        message.setDirection(new double[] { 1.0, 2.0, 3.0 });
        message.setLength(4.0);
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"app_translated\", \"appId\": \"foo\", \"direction\": [1.0, 2.0, 3.0], \"length\": 4.0 }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedComponentUpdateMessage() throws EncodeException, IOException {
        final var message = new ComponentUpdateMessage();
        message.setAppID("foo");
        message.setComponentID("bar");
        message.setIsOpened(true);
        message.setIsFoundation(true);
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"component_update\", \"appID\": \"foo\", \"componentID\": \"bar\", \"isOpened\": true, \"isFoundation\": true }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedNodegroupUpdateMessage() throws EncodeException, IOException {
        final var message = new NodegroupUpdateMessage();
        message.setId("foo");
        message.setIsOpen(true);
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"nodegroup_update\", \"id\": \"foo\", \"isOpen\": true }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedLandscapePositionMessage() throws EncodeException, IOException {
        final var message = new LandscapePositionMessage();
        message.setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"landscape_position\", \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedSpectatingUpdateMessage() throws EncodeException, IOException {
        final var message = new SpectatingUpdateMessage();
        message.setUserID("foo");
        message.setIsSpectating(true);
        message.setSpectatedUser("bar");
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"spectating_update\", \"userID\": \"foo\", \"isSpectating\": true, \"spectatedUser\": \"bar\" }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedSystemUpdateMessage() throws EncodeException, IOException {
        final var message = new SystemUpdateMessage();
        message.setId("foo");
        message.setIsOpen(true);
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"system_update\", \"id\": \"foo\", \"isOpen\": true }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedUserControllersMessage() throws EncodeException, IOException {
        final var message = new UserControllersMessage();
        message.setConnect(new Controllers());
        message.getConnect().setController1("oculus-left");
        message.getConnect().setController2("oculus-right");
        message.setDisconnect(new Controllers());
        message.getDisconnect().setController1("vive-left");
        message.getDisconnect().setController2("vive-right");
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"user_controllers\", "
                + "\"connect\": {\"controller1\": \"oculus-left\", \"controller2\": \"oculus-right\"}, "
                + "\"disconnect\": {\"controller1\": \"vive-left\", \"controller2\": \"vive-right\"} }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testForwardedUserPositionsMessage() throws EncodeException, IOException {
        final var message = new UserPositionsMessage();
        message.setController1(new UserPositionsMessage.Pose());
        message.getController1().setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.getController1().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.setController2(new UserPositionsMessage.Pose());
        message.getController2().setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.getController2().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.setCamera(new UserPositionsMessage.Pose());
        message.getCamera().setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.getCamera().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.setTime(new Date(884300400000L));
        final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
        final var expected = "{ \"event\": \"forward\", \"userID\": \"alice\", \"originalMessage\": "
                + "{ \"event\": \"user_positions\", "
                + "\"controller1\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"controller2\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"camera\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] },"
                + "\"time\": 884300400000 }}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testSelfConnectedMessage() throws EncodeException, IOException {
        final var message = new SelfConnectedMessage();

        // Add current user.
        message.setSelf(new SelfConnectedMessage.User());
        message.getSelf().setId("foo");
        message.getSelf().setName("alice");
        message.getSelf().setColor(new Color(0, 42, 148));

        // Add one other user.
        message.setUsers(new SelfConnectedMessage.OtherUser[] { new SelfConnectedMessage.OtherUser() });
        message.getUsers()[0].setId("bar");
        message.getUsers()[0].setName("bob");
        message.getUsers()[0].setColor(new Color(148, 42, 0));
        message.getUsers()[0].setControllers(new SelfConnectedMessage.Controllers());
        message.getUsers()[0].getControllers().setController1("baz");
        message.getUsers()[0].getControllers().setController2("qux");

        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"self_connected\", \"self\": {" + " \"id\": \"foo\", \"name\": \"alice\", "
                + "\"color\": [0.0, 0.16470588235294117, 0.5803921568627451]"
                + "}, \"users\": [{\"id\": \"bar\", \"name\": \"bob\", "
                + "\"color\": [0.5803921568627451, 0.16470588235294117, 0.0], "
                + "\"controllers\": { \"controller1\": \"baz\", \"controller2\": \"qux\" }}]}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testUserConnectedMessage() throws EncodeException, IOException {
        final var message = new UserConnectedMessage();
        message.setId("foo");
        message.setName("bar");
        message.setColor(new Color(0, 42, 148));
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"user_connected\", \"id\": \"foo\", \"name\": \"bar\", "
                + "\"color\": [0.0, 0.16470588235294117, 0.5803921568627451] }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testUserDisconnectedMessage() throws EncodeException, IOException {
        final var message = new UserDisconnectedMessage();
        message.setId("foo");
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"user_disconnect\", \"id\": \"foo\" }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testSendLandscapeMessage() throws EncodeException, IOException {
        final var message = new SendLandscapeMessage();

        // Create a test system.
        message.setSystems(new SendLandscapeMessage.LandscapeEntity[] { new SendLandscapeMessage.LandscapeEntity() });
        message.getSystems()[0].setId("foo");
        message.getSystems()[0].setOpened(true);

        // Create a test node group.
        message.setNodeGroups(
                new SendLandscapeMessage.LandscapeEntity[] { new SendLandscapeMessage.LandscapeEntity() });
        message.getNodeGroups()[0].setId("bar");
        message.getNodeGroups()[0].setOpened(true);

        // Create a test app.
        message.setOpenApps(new SendLandscapeMessage.App[] { new SendLandscapeMessage.App() });
        message.getOpenApps()[0].setId("baz");
        message.getOpenApps()[0].setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.getOpenApps()[0].setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.getOpenApps()[0].setOpenComponents(new String[] { "x", "y", "z" });

        // Create a highlighted component.
        message.getOpenApps()[0].setHighlightedComponents(
                new SendLandscapeMessage.HighlightingObject[] { new SendLandscapeMessage.HighlightingObject() });
        message.getOpenApps()[0].getHighlightedComponents()[0].setUserID("alice");
        message.getOpenApps()[0].getHighlightedComponents()[0].setAppID("baz");
        message.getOpenApps()[0].getHighlightedComponents()[0].setEntityType("v");
        message.getOpenApps()[0].getHighlightedComponents()[0].setEntityID("w");
        message.getOpenApps()[0].getHighlightedComponents()[0].setHighlighted(true);

        // Set landscape position and rotation.
        message.setLandscape(new SendLandscapeMessage.LandscapePosition());
        message.getLandscape().setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.getLandscape().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });

        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"landscape\", \"systems\": [{\"id\": \"foo\", \"opened\": true}], "
                + " \"nodeGroups\": [{\"id\": \"bar\", \"opened\": true}], "
                + " \"openApps\": [{\"id\": \"baz\", \"position\": [1.0, 2.0, 3.0], "
                + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0], \"openComponents\": [\"x\", \"y\", \"z\"], "
                + "    \"highlightedComponents\": [{"
                + "        \"userID\": \"alice\", \"appID\": \"baz\", \"entityType\": \"v\", "
                + "        \"entityID\": \"w\", \"isHighlighted\": true }]"
                + " }], \"landscape\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0]} }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }
}
