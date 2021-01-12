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

public class VRMessageEncoderTest {
    /**
     * A custom comparator for strings that
     */
    private static Comparator<String> ignoreWhitespace = new Comparator<>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.replaceAll("\\s+", "").compareTo(s2.replaceAll("\\s+", ""));
        }
    };

    private VRMessageEncoder encoder;

    @BeforeEach
    void initEncodwr() {
        encoder = new VRMessageEncoder();
        encoder.init(new EndpointConfig() {
            @Override
            public Map<String, Object> getUserProperties() {
                return new HashMap<>();
            }

            @Override
            public List<Class<? extends Encoder>> getEncoders() {
                return Arrays.asList(VRMessageEncoder.class);
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
    public void testForwardedMessage() throws EncodeException, IOException {
        final var originalMessage = new AppClosedMessage();
        originalMessage.setAppID("bar");
        final var message = new ForwardedMessage();
        message.setEvent("forward");
        message.setUserID("foo");
        message.setOriginalMessage(originalMessage);
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"forward\", \"userID\": \"foo\", \"originalMessage\": { \"event\": \"app_closed\", \"appID\": \"bar\" } }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testAppClosesdMessage() throws EncodeException, IOException {
        final var message = new AppClosedMessage();
        message.setAppID("foo");
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"app_closed\", \"appID\": \"foo\" }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testAppGrabbedMessage() throws EncodeException, IOException {
        final var message = new AppGrabbedMessage();
        message.setAppID("foo");
        message.setAppPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setAppQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        message.setIsGrabbedByController1(true);
        message.setControllerPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setControllerQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"app_grabbed\", \"appID\": \"foo\", \"appPosition\": [1.0, 2.0, 3.0],"
                + "  \"appQuaternion\": [1.0, 2.0, 3.0, 4.0],"
                + "  \"isGrabbedByController1\": true, \"controllerPosition\": [1.0, 2.0, 3.0],"
                + "  \"controllerQuaternion\": [1.0, 2.0, 3.0, 4.0] }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testAppOpenedMessage() throws EncodeException, IOException {
        final var message = new AppOpenedMessage();
        message.setId("foo");
        message.setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"app_opened\", \"id\": \"foo\", \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testAppReleasedMessage() throws EncodeException, IOException {
        final var message = new AppReleasedMessage();
        message.setId("foo");
        message.setPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"app_released\", \"id\": \"foo\",  \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testAppTranslatedMessage() throws EncodeException, IOException {
        final var message = new AppTranslatedMessage();
        message.setAppId("foo");
        message.setDirection(new double[] { 1.0, 2.0, 3.0 });
        message.setLength(4.0);
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"app_translated\", \"appId\": \"foo\", \"direction\": [1.0, 2.0, 3.0], \"length\": 4.0 }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testComponentUpdateMessage() throws EncodeException, IOException {
        final var message = new ComponentUpdateMessage();
        message.setAppID("foo");
        message.setComponentID("bar");
        message.setIsOpened(true);
        message.setIsFoundation(true);
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"component_update\", \"appID\": \"foo\", \"componentID\": \"bar\", \"isOpened\": true, \"isFoundation\": true }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testNodegroupUpdateMessage() throws EncodeException, IOException {
        final var message = new NodegroupUpdateMessage();
        message.setId("foo");
        message.setIsOpen(true);
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"nodegroup_update\", \"id\": \"foo\", \"isOpen\": true }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testLandscapePositionMessage() throws EncodeException, IOException {
        final var message = new LandscapePositionMessage();
        message.setDeltaPosition(new double[] { 1.0, 2.0, 3.0 });
        message.setOffset(new double[] { 1.0, 2.0, 3.0 });
        message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"landscape_position\", \"deltaPosition\": [1.0, 2.0, 3.0], \"offset\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testSpectatingUpdateMessage() throws EncodeException, IOException {
        final var message = new SpectatingUpdateMessage();
        message.setUserID("foo");
        message.setIsSpectating(true);
        message.setSpectatedUser("bar");
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"spectating_update\", \"userID\": \"foo\", \"isSpectating\": true, \"spectatedUser\": \"bar\" }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testSystemUpdateMessage() throws EncodeException, IOException {
        final var message = new SystemUpdateMessage();
        message.setId("foo");
        message.setIsOpen(true);
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"system_update\", \"id\": \"foo\", \"isOpen\": true }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testUserControllersMessage() throws EncodeException, IOException {
        final var message = new UserControllersMessage();
        message.setConnect(new Controllers());
        message.getConnect().setController1("oculus-left");
        message.getConnect().setController2("oculus-right");
        message.setDisconnect(new Controllers());
        message.getDisconnect().setController1("vive-left");
        message.getDisconnect().setController2("vive-right");
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"user_controllers\", "
                + "\"connect\": {\"controller1\": \"oculus-left\", \"controller2\": \"oculus-right\"}, "
                + "\"disconnect\": {\"controller1\": \"vive-left\", \"controller2\": \"vive-right\"} }";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }

    @Test
    public void testUserPositionsMessage() throws EncodeException, IOException {
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
        final var actual = encoder.encodeMessage(message);
        final var expected = "{ \"event\": \"user_positions\", "
                + "\"controller1\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"controller2\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"camera\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] },"
                + "\"time\": 884300400000}";
        assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
    }
}
